package com.foodquest.models;

/**
 * <p>
 * This is a Pair class to hold key value pair
 * </p>
 * 
 * @author Shreyas K
 */
public class Pair<L, R> {
	private final L left;
	private final R right;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	@Override
	public String toString() {
		return "Pair [left=" + left + ", right=" + right + "]";
	}
}
