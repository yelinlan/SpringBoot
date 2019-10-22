package com.hqyj.shiro.modules.test.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;
import com.hqyj.shiro.modules.test.entity.City;
import com.hqyj.shiro.modules.test.entity.Country;
import com.hqyj.shiro.modules.test.service.TestService;
import com.hqyj.shiro.modules.test.vo.ApplicationTest;

@Controller
@RequestMapping("/mytest")
public class TestController {
	@Autowired
	private ApplicationTest applicationTest;
	@Autowired
	private TestService testService;
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@Value("${server.port}")
	private int port;
	@Value("${com.hqyj.name}")
	private String name;
	@Value("${com.hqyj.age}")
	private int age;
	@Value("${com.hqyj.description}")
	private String description;
	@Value("${com.hqyj.random}")
	private String random;
	
	/**
	 * 下载文件
	 * 响应头信息
	 * 'Content-Type': 'application/octet-stream',
	 * 'Content-Disposition': 'attachment;filename=req_get_download.js'
	 * @return ResponseEntity ---- spring专门包装响应信息的类
	 */
	@RequestMapping("/download")
	@ResponseBody
	public  ResponseEntity<Resource> downloadFile(@RequestParam String fileName){
		
		try {
			UrlResource resource = new UrlResource(Paths.get("F:/upload/"+fileName).toUri());
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" 
					+ fileName + "\"" ).body(resource);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	/**
	 * 上传多个文件
	 * @param files
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value = "/uploadBatchFile", consumes = "multipart/form-data")
	public String uploadBatchFile(@RequestParam MultipartFile[] files,RedirectAttributes redirectAttributes){
		boolean empty = true;
		for (MultipartFile file : files) {
			if(file.isEmpty()){
				continue;
			}
			try {
				File f = new File("F:/upload/");
				if(!f.exists()){
					f.mkdir();
				};
				String desPath = "F:/upload/"+file.getOriginalFilename();
				File desFile = new File(desPath);
				file.transferTo(desFile);
				empty = false;
			}  catch (IllegalStateException | IOException e) {
				redirectAttributes.addFlashAttribute("message","Upload file fail.");
				return "redirect:/mytest/index";
			} 
		}
		if (empty) {
			redirectAttributes.addFlashAttribute("message","Please select file.");
		} else {
			redirectAttributes.addFlashAttribute("message","Upload file success.");
		}
		return "redirect:/mytest/index";
	}
	
	/**
	 * 上传单个文件，虽然是form表单，但file是以参数的形式传递的，采用requestParam注解接收MultipartFile
	 */
	@PostMapping(value = "/upload",consumes = "multipart/form-data")
	public String uploadFile(@RequestParam MultipartFile file,RedirectAttributes redirectAttributes){
		if(file.isEmpty()){
			redirectAttributes.addFlashAttribute("message","Please select file.");
			return "redirect:/mytest/index";
		}
		
		try {
//			String desPath = "F:/upload"+File.separator+file.getOriginalFilename();
			File f = new File("F:/upload/");
			if(!f.exists()){
				f.mkdir();
			};
			String desPath = "F:/upload/"+file.getOriginalFilename();
			File desFile = new File(desPath);
			file.transferTo(desFile);
		} catch (IllegalStateException | IOException e) {
			redirectAttributes.addFlashAttribute("message","Upload file fail.");
			return "redirect:/mytest/index";
		} 
		
		redirectAttributes.addFlashAttribute("message","Upload file success.");
		return "redirect:/mytest/index";
	}
	
	/**
	 * thymeleaf 
	 */
	@RequestMapping("/index")
	public String testIndex(ModelMap modelMap){
		modelMap.addAttribute("thymeleafTitle", "welcome to thymeleaf");
		modelMap.addAttribute("checked",true);
		modelMap.addAttribute("currentNumber",50);
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("baiduUrl","http://baidu.com");
		modelMap.addAttribute("template","/test/iNdEx");//拦截器处理自动小写
		
		int countryId=522;
		Country country = testService.getCountryByCountrtyId(countryId);
		modelMap.addAttribute("country",country);
		
		List<City> cities = testService.getCities(522);
		modelMap.addAttribute("updateCityUri","/mytest/city2");
		modelMap.addAttribute("city",cities.get(0));
		//
		cities = cities.stream().limit(10).collect(Collectors.toList());
		modelMap.addAttribute("cities",cities);
		
		modelMap.addAttribute("shopLogo","https://js.tuguaishou.com/"
				+ "index_img/chrome-logo.png");
		
		return "index";
		
	}
	
	@RequestMapping("/city/{cityId}")
	@ResponseBody
	public int deleteCity(@PathVariable int cityId) {
		return testService.deleteCity(cityId);
	}
	
	/**
	 * requestBody接收form数据
	 */
	@RequestMapping(value = "/city2" , consumes = "application/x-www-form-urlencoded" , 
			method = RequestMethod.POST)
	public String updateCity(@ModelAttribute City city){
		testService.updateCity(city);
		return "redirect:/mytest/index";
	}
	
	/**consumes ----进入方法的数据类型
	 * produces-----方法返回数据类型
	 * 常见类型（application/json、application/x-www-form-urlencoded）
	 * requestbody------接收json数据
	 * @param city
	 * @return
	 */
	@PostMapping(value = "/city" , consumes = "application/json")
	@ResponseBody
	public City insertCity(@RequestBody City city){
		testService.insertCity(city);
		return city;
	}
	
	//http://localhost/mytest/page/cities?currentPage=1&pageSize=2	
	@RequestMapping("/page/cities")
	@ResponseBody
	public PageInfo<City> getCitiesByPage(@RequestParam int currentPage,@RequestParam int pageSize){
		
		return testService.getCitiesByPage(currentPage, pageSize);
	}
	
	@RequestMapping("/cities/{countryId}")
	@ResponseBody
	public List<City> getCitiesByCountryId(@PathVariable int countryId){
		return testService.getCities(countryId);
	}
	
	@RequestMapping("/country/{countryId}")
	@ResponseBody
	public Country getCountryByCountrtyId(@PathVariable int countryId) {
		return testService.getCountryByCountrtyId(countryId);
	}
	
	@RequestMapping("/country")
	@ResponseBody
	public Country getCountryByCountryName(@RequestParam String countryName) {
		
		return testService.getCountryByCountryName(countryName);
	}
	
	
	@RequestMapping("/Config")
	@ResponseBody
	public String getConfig() {
		return "" + port + name + age + description + random;
	}

	@RequestMapping("/ConfigOther")
	@ResponseBody
	public String getConfigOther() {
		return "" + applicationTest.getPort() + applicationTest.getName() + applicationTest.getAge()
				+ applicationTest.getDescription() + applicationTest.getRandom();
	}

	@RequestMapping("/info")
	@ResponseBody
	public String testInfo() {
		return "hello SpringBoot!";
	}

	@RequestMapping("/log")
	@ResponseBody
	public String testLog() {
		System.err.println("------------------------------");
		LOGGER.trace("This is trace log");
		LOGGER.debug("This is debug log");
		LOGGER.info("This is info log");
		LOGGER.warn("This is warn log");
		LOGGER.error("This is error log");
		return "This is  log test";
	}
	
	@RequestMapping(value="/postTest",method=RequestMethod.POST)
	@ResponseBody
	public String postTest() {	
		return "This is  postTest test";
	}
	@RequestMapping(value="/postTest1")
	@PostMapping("/post")
	@ResponseBody
	public String postTest1() {	
		return "This is  postTest1 test";
	}
	
	//过滤
	@RequestMapping(value="/getTest")//OK
	@ResponseBody
	public String getTest(HttpServletRequest request) {
		return request.getParameter("name");
	}

}
