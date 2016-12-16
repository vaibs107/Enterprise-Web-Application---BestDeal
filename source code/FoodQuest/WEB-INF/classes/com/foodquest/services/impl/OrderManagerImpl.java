package com.foodquest.services.impl;

import java.util.List;

import com.foodquest.models.LocationBean;
import com.foodquest.models.OrderBean;
import com.foodquest.models.OrderBean.OrderStatus;
import com.foodquest.models.OrderItem;
import com.foodquest.models.RecipeBean;
import com.foodquest.repository.ILocationRepository;
import com.foodquest.repository.IOrderRepository;
import com.foodquest.repository.IRecipeRepository;
import com.foodquest.repository.impl.LocationRepositoryImpl;
import com.foodquest.repository.impl.OrderRepositoryImpl;
import com.foodquest.repository.impl.RecipeRepositoryImpl;
import com.foodquest.services.IOrderManager;
import com.foodquest.utils.CommonUtil;
import com.foodquest.utils.GeoCodeUtil;
import com.foodquest.utils.lang.AppException;

public class OrderManagerImpl implements IOrderManager {
	private static IOrderManager INSTANCE;
	private IOrderRepository orderRepository;
	private ILocationRepository locationRepository;
	private IRecipeRepository recipeRepository;

	private OrderManagerImpl() {
		orderRepository = OrderRepositoryImpl.getInstance();
		locationRepository = LocationRepositoryImpl.getInstance();
		recipeRepository = RecipeRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(OrderBean order) throws AppException {
		System.out.println("Create order :: " + order);
		order.calculateTotal();
		order.setStatus(OrderStatus.IN_CART);
		order.setCreatedAt(CommonUtil.getCurrentTimeInMillis());
		order.setUpdatedAt(CommonUtil.getCurrentTimeInMillis());
		order.setDeliveryAddress(order.getCustomer().getLocation());

		int id = orderRepository.create(order);

		for (OrderItem item : order.getItems()) {
			updateBookingsCount(item.getRecipe());
		}
		return id;
	}

	@Override
	public OrderBean findById(int id) throws AppException {
		return orderRepository.findById(id);
	}

	@Override
	public OrderBean findOrderInCartByUserId(int userId) throws AppException {
		return orderRepository.findOrderInCartByUserId(userId);
	}

	@Override
	public OrderBean findByCnf(String cnf) throws AppException {
		return orderRepository.findByCnf(cnf);
	}

	@Override
	public List<OrderBean> findAll(OrderStatus status) throws AppException {
		return orderRepository.findAll(status);
	}

	@Override
	public Boolean update(OrderBean order) throws AppException {
		OrderBean _order = findById(order.getId());
		if (order.getConfirmationNumber() != null) {
			_order.setConfirmationNumber(order.getConfirmationNumber());
		}
		if (order.getDeliveryAddress() != null) {
			_order.setDeliveryAddress(order.getDeliveryAddress());
		}
		if (order.getItems() != null) {
			_order.setItems(order.getItems());
		}
		if (order.getOrderedAt() != null) {
			_order.setOrderedAt(order.getOrderedAt());
		}
		if (order.getStatus() != null) {
			_order.setStatus(order.getStatus());
		}
		if (order.getTotal() != null) {
			_order.setTotal(order.getTotal());
		}
		_order.calculateTotal();
		for (OrderItem item : _order.getItems()) {
			updateBookingsCount(item.getRecipe());
		}
		return orderRepository.update(_order);
	}

	@Override
	public Boolean delete(int id) throws AppException {
		return orderRepository.delete(id);
	}

	@Override
	public Boolean updateOrderItem(int orderId, int recipeId, boolean shouldAdd, int quantity) throws AppException {
		OrderBean order = findById(orderId);
		if (order == null) {
			return false;
		}
		for (OrderItem item : order.getItems()) {
			if (item.getRecipe().getId() == recipeId) {
				if (shouldAdd) {
					item.setQuantity(quantity);
					item.setPrice(item.getPrice());
				} else {
					order.getItems().remove(item);
				}
				break;
			}
		}
		order.calculateTotal();
		update(order);
		if (order.getItems().size() == 0) {
			System.out.println("Do delete");
			delete(orderId);
		}
		return true;
	}

	@Override
	public OrderBean placeOrder(int id, LocationBean location) throws AppException {
		OrderBean order = findById(id);
		if (order == null) {
			return null;
		}

		order.setOrderedAt(CommonUtil.getCurrentTimeInMillis());
		order.setConfirmationNumber(CommonUtil.generateRandomAlphanumeric(4));
		if (location != null) {
			GeoCodeUtil.doCalculateLatLon(location);
			location.setId(locationRepository.create(location));
			order.setDeliveryAddress(location);
		}
		order.setStatus(OrderStatus.CONFIRMED);
		update(order);
		return order;
	}

	private void updateBookingsCount(RecipeBean recipe) throws AppException {
		recipe.setTotalBookings(orderRepository.getTotalBookingsCount(recipe));
		recipeRepository.update(recipe);

	}

	public static IOrderManager getInstance() {
		synchronized (OrderManagerImpl.class) {
			if (INSTANCE == null) {
				INSTANCE = new OrderManagerImpl();
			}
		}
		return INSTANCE;
	}
}
