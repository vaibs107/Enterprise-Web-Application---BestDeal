package com.foodquest.models;

import java.util.LinkedHashSet;
import java.util.Set;

import com.foodquest.utils.CommonUtil;

public class OrderBean {
	private Integer id;
	private Set<OrderItem> items;
	protected UserBean customer;
	private Double total;
	private OrderStatus status;
	private String confirmationNumber;
	private Long createdAt;
	private Long updatedAt;
	private Long orderedAt;
	private LocationBean deliveryAddress;

	public enum OrderStatus {
		IN_CART, CONFIRMED, DELIVERED, CANCELLED
	}

	public OrderBean() {
		items = new LinkedHashSet<OrderItem>();
	}

	public void addItem(RecipeBean recipe, Integer quantity) {
		OrderItem item = new OrderItem(recipe, quantity);
		OrderItem _item = null;
		boolean isNew = true;
		for (OrderItem itm : getItems()) {
			if (itm.getRecipe().getId().equals(recipe.getId())) {
				isNew = false;
				_item = itm;
			}
		}
		if (!isNew) {
			getItems().remove(_item);
		}
		getItems().add(item);
	}

	public void calculateTotal() {
		this.total = 0D;
		for (OrderItem item : items) {
			this.total += item.getPrice();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public UserBean getCustomer() {
		return customer;
	}

	public void setCustomer(UserBean customer) {
		this.customer = customer;
	}

	public Double getTotal() {
		return CommonUtil.formatDecimalValue(total);
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(Long orderedAt) {
		this.orderedAt = orderedAt;
	}

	public LocationBean getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(LocationBean deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBean other = (OrderBean) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", items=" + items + ", customer=" + customer + ", total=" + total + ", status="
				+ status + ", confirmationNumber=" + confirmationNumber + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", orderedAt=" + orderedAt + ", deliveryAddress=" + deliveryAddress + "]";
	}

}
