package com.hqyj.shiro.modules.test.dao;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hqyj.shiro.modules.test.entity.City;
import com.hqyj.shiro.modules.test.entity.Country;

@Repository
@Mapper
public interface TestDao {

	@Select("select * from m_city where country_id=#{countryId}")//city块
	public List<City> getCities(int countryId);
	
	@Select("select * from m_country where country_id=#{countryId}")//country块--1
	@Results(id="countyResult",value={
			@Result(column="country_id",property="countryId"),
			
			@Result(property="cities",
					column="country_id",//city块--1
					javaType=List.class,
					many=@Many(select="com.hqyj.shiro.modules.test.dao.TestDao.getCities")
			)
	})
	public Country getCountryByCountrtyId(int countryId);
	
	@Select("select * from m_country where country_name=#{countryName}")//country块--2
	@ResultMap(value="countyResult")//city块--1
	public Country getCountryByCountryName(String countryName);
	
	/**
	 * 分页
	 */
	@Select("SELECT *FROM m_city")
	List<City> getCitiesByPage();
	
	/**
	 * useGeneratedKeys：包装插入id
	 * 自动生成一个cityID
	 */
	@Insert("insert m_city(city_name,country_id,date_created) "
			+ "values(#{cityName},#{countryId},#{dateCreated})")
	@Options(useGeneratedKeys=true,keyColumn="city_id",keyProperty="cityId")
	void insertCity(City city);
	
	@Update("UPDATE m_city set local_city_name = #{localCityName} where city_id = #{cityId}")
	void updateCity(City city);
	
	@Delete("DELETE FROM m_city where city_id = #{cityId}")
	int deleteCity(int cityId);
	
	/**
	 * 配置文件方式
	 * application.properties
	 * mybatis.type-aliases-package= com.hqyj.shiro.modules.*.entity
	 * mybatis.mapper-locations=classpath:config/*Mapper.xml
	 * 读取cityMapper.xml，方法名和mapper中设置的id一致
	 */
	List<City> getCities2(int countryId);
}
