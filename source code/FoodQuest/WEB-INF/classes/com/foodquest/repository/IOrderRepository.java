package com.foodquest.repository;

import java.util.List;

import com.foodquest.models.OrderBean;
import com.foodquest.models.OrderBean.OrderStatus;
import com.foodquest.models.RecipeBean;
import com.foodquest.utils.lang.AppException;

public interface IOrderRepository {
	Integer create(OrderBean order) throws AppException;

	OrderBean findById(int id) throws AppException;

	OrderBean findOrderInCartByUserId(int userId) throws AppException;

	OrderBean findByCnf(String cnf) throws AppException;

	List<OrderBean> findAll(OrderStatus status) throws AppException;

	boolean update(OrderBean order) throws AppException;

	boolean delete(int id) throws AppException;

	int getTotalBookingsCount(RecipeBean recipe) throws AppException;
}
