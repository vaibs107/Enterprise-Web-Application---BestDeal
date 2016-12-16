package com.foodquest.models;

import com.foodquest.utils.CommonUtil;
import com.foodquest.utils.ObjectCloner;

public class OrderItem {
	private RecipeBean recipe;
	private Integer quantity;
	private Double price;

	public OrderItem(RecipeBean recipe) {
		this(recipe, 1);
	}

	public OrderItem(RecipeBean recipe, int quantity) {
		try {
			this.recipe = (RecipeBean) ObjectCloner.deepCopy(recipe);
		} catch (Exception e) {
		}
		this.quantity = quantity;
	}

	public RecipeBean getRecipe() {
		return recipe;
	}

	public void setRecipe(RecipeBean recipe) {
		this.recipe = recipe;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		price = recipe.getPrice() * quantity;
		return CommonUtil.formatDecimalValue(price);
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderItem [recipe=" + recipe + ", quantity=" + quantity + ", price=" + price + "]";
	}
}
