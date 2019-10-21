package com.hqyj.shiro.modules.test.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hqyj.shiro.modules.test.entity.City;
import com.hqyj.shiro.modules.test.entity.Country;

public interface TestService {
	
	public List<City> getCities(int countryId);
	
	public Country getCountryByCountrtyId(int countryId);
	
	public Country getCountryByCountryName(String countryName);
	
	PageInfo<City> getCitiesByPage(int currentPage,int pageSize);
	
	void insertCity(City city);
	
	void updateCity(City city);
	
	int deleteCity(int cityId);
	
}
