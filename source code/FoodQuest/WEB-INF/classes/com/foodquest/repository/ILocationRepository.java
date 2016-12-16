package com.foodquest.repository;

import com.foodquest.models.LocationBean;
import com.foodquest.utils.lang.AppException;

public interface ILocationRepository {
	Integer create(LocationBean bean) throws AppException;

	LocationBean findById(Integer id) throws AppException;

	Boolean update(LocationBean bean) throws AppException;

	Boolean delete(Integer id) throws AppException;
}
