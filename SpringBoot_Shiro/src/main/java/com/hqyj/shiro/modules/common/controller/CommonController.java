package com.hqyj.shiro.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

	@RequestMapping(value = "/error/403")
	public String authorizationErrorPage(ModelMap modelMap ){
		modelMap.addAttribute("template", "error/403");
		return "index";
	}
}
