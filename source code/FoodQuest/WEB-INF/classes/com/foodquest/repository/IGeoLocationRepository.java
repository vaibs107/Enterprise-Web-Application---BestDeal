package com.foodquest.repository;

import java.util.List;

import com.foodquest.models.LocationBean;
import com.foodquest.utils.lang.AppException;

public interface IGeoLocationRepository {
	String create(LocationBean location) throws AppException;

	LocationBean findById(Integer id) throws AppException;

	Boolean update(LocationBean location) throws AppException;

	Boolean delete(Integer id) throws AppException;

	List<LocationBean> search(LocationBean location, Double radius) throws AppException;
}
