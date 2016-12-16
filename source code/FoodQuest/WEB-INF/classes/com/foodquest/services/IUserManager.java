package com.foodquest.services;

import java.util.List;

import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.AccountStatus;
import com.foodquest.utils.lang.AppException;

/**
* IUserManager sets up user manager contract
*
* @author  Shreyas
*/
public interface IUserManager {
	/*
	 * create user 
	 * @param  user  bean to be created
	 * @return the id of the created user
	 * @throws AppException
	 */
	Integer create(UserBean user) throws AppException;
	
	/*
	 * finds user by id
	 * @param  id  of the user to be searched
	 * @return the user bean
	 * @throws AppException
	 */
	UserBean findById(Integer id) throws AppException;
	
	/*
	 * finds user bean by email
	 * @param  email  of the user to be searched
	 * @return the user bean
	 * @throws AppException
	 */
	UserBean findByEmail(String email) throws AppException;
	
	/*
	 * read all users
	 * @return the list of user beans
	 * @throws AppException
	 */
	List<UserBean> findAll(AccountStatus status) throws AppException;
	
	/*
	 * update user 
	 * @param  user bean to be updated
	 * @return boolean of the operation
	 * @throws AppException
	 */
	Boolean update(UserBean user) throws AppException;
	
	/*
	 * deletes user by id
	 * @param  id  of the user to be deleted
	 * @return boolean of the operation
	 * @throws AppException
	 */
	Boolean delete(Integer id) throws AppException;
	
	/*
	 * create user 
	 * @param  user  user bean to be created
	 * @return user bean of the authenticated user
	 * @throws AppException
	 */
	UserBean authenticateByEmail(String email, String password) throws AppException;
}
