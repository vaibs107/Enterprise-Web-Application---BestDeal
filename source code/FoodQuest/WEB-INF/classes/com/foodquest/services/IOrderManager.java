package com.foodquest.services;

import java.util.List;

import com.foodquest.models.LocationBean;
import com.foodquest.models.OrderBean;
import com.foodquest.models.OrderBean.OrderStatus;
import com.foodquest.utils.lang.AppException;

public interface IOrderManager {
	Integer create(OrderBean order) throws AppException;

	OrderBean findById(int id) throws AppException;

	OrderBean findOrderInCartByUserId(int userId) throws AppException;

	OrderBean findByCnf(String cnf) throws AppException;

	List<OrderBean> findAll(OrderStatus status) throws AppException;

	Boolean update(OrderBean order) throws AppException;

	Boolean delete(int id) throws AppException;

	Boolean updateOrderItem(int orderId, int recipeId, boolean shouldAdd, int quantity) throws AppException;

	OrderBean placeOrder(int id, LocationBean location) throws AppException;
}
