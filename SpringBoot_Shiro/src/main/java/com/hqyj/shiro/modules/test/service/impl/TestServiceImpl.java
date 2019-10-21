package com.hqyj.shiro.modules.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.shiro.modules.test.dao.TestDao;
import com.hqyj.shiro.modules.test.entity.City;
import com.hqyj.shiro.modules.test.entity.Country;
import com.hqyj.shiro.modules.test.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao; 
	
	@Override
	public List<City> getCities(int countryId) {
//		return testDao.getCities(countryId);
		return testDao.getCities2(countryId);
	}
	
	@Override
	public Country getCountryByCountrtyId(int countryId) {
		return testDao.getCountryByCountrtyId(countryId);
	}
	
	@Override
	public Country getCountryByCountryName(String countryName) {
		return testDao.getCountryByCountryName(countryName);
	}
	
	/**
	 * 分页
	 */
	@Override
	public PageInfo<City> getCitiesByPage(int currentPage, int pageSize) {
		PageHelper.startPage(currentPage,pageSize);
		List<City> cities = testDao.getCitiesByPage();
		PageInfo<City> pageInfo = new PageInfo<>(cities);
		return pageInfo;
	}

	@Override
	public void insertCity(City city) {
		testDao.insertCity(city);
	}

	@Override
	@Transactional
	(noRollbackFor= ArithmeticException.class,propagation = Propagation.REQUIRED)
	public void updateCity(City city) {
		testDao.updateCity(city);
//		int i=1/0;
	}

	@Override
	public int deleteCity(int cityId) {
		return testDao.deleteCity(cityId);
	}

	
}
