package com.foodquest.repository;

import java.util.List;

import com.foodquest.models.DistributorBean;
import com.foodquest.models.DistributorBean.DistributorStatus;
import com.foodquest.models.LocationBean;
import com.foodquest.utils.lang.AppException;

public interface IDistributorRepository {
	/*
	 * create bean
	 * 
	 * @param bean bean to be created
	 * 
	 * @return the id of the created bean
	 * 
	 * @throws AppException
	 */
	Integer create(DistributorBean bean) throws AppException;

	/*
	 * finds bean by id
	 * 
	 * @param id of the bean to be searched
	 * 
	 * @return the bean bean
	 * 
	 * @throws AppException
	 */
	DistributorBean findById(Integer id) throws AppException;
	
	DistributorBean findByName(String name) throws AppException;
	
	DistributorBean findByLocation(LocationBean location) throws AppException;
	/*
	 * read all beans
	 * 
	 * @return the list of bean beans
	 * 
	 * @throws AppException
	 */
	List<DistributorBean> findAll(DistributorStatus status, String name) throws AppException;

	/*
	 * update bean
	 * 
	 * @param bean bean to be updated
	 * 
	 * @return boolean of the operation
	 * 
	 * @throws AppException
	 */
	Boolean update(DistributorBean bean) throws AppException;

	/*
	 * deletes bean by id
	 * 
	 * @param id of the bean to be deleted
	 * 
	 * @return boolean of the operation
	 * 
	 * @throws AppException
	 */
	Boolean delete(Integer id) throws AppException;

	Boolean createUserDistributorMapping(Integer distributorId, Integer userId) throws AppException;
	
	List<Integer> getUserIds(Integer distributorId) throws AppException;
}
