package com.foodquest.models;

import java.io.Serializable;

public class RecipeBean implements Serializable{
	private static final long serialVersionUID = 2L;
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer totalServings;
	private Integer totalBookings;
	private CategoryBean category;
	private CusineTypeBean cusineType;
	private Long dateOfServing;
	private RecipeStatus status;
	private ImageBean image;
	private UserBean owner;
	private Long createdAt;
	private Long updatedAt;
	private RecipeDistributorStatus recipeDistributorStatus;
	private DistributorBean distributor;

	public enum RecipeStatus {
		PENDING, PUBLISHED, CANCELLED, COMPLETED
	}

	public enum RecipeDistributorStatus {
		UNASSIGNED, ASSIGNED, PICKED, DISPATCHED
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getTotalServings() {
		return totalServings;
	}

	public void setTotalServings(Integer totalServings) {
		this.totalServings = totalServings;
	}

	public CategoryBean getCategory() {
		return category;
	}

	public void setCategory(CategoryBean category) {
		this.category = category;
	}

	public CusineTypeBean getCusineType() {
		return cusineType;
	}

	public void setCusineType(CusineTypeBean cusineType) {
		this.cusineType = cusineType;
	}

	public Long getDateOfServing() {
		return dateOfServing;
	}

	public void setDateOfServing(Long dateOfServing) {
		this.dateOfServing = dateOfServing;
	}

	public RecipeStatus getStatus() {
		return status;
	}

	public void setStatus(RecipeStatus status) {
		this.status = status;
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

	public UserBean getOwner() {
		return owner;
	}

	public void setOwner(UserBean owner) {
		this.owner = owner;
	}

	public ImageBean getImage() {
		return image;
	}

	public void setImage(ImageBean image) {
		this.image = image;
	}

	public RecipeDistributorStatus getRecipeDistributorStatus() {
		return recipeDistributorStatus;
	}

	public void setRecipeDistributorStatus(RecipeDistributorStatus recipeDistributorStatus) {
		this.recipeDistributorStatus = recipeDistributorStatus;
	}

	public DistributorBean getDistributor() {
		return distributor;
	}

	public void setDistributor(DistributorBean distributor) {
		this.distributor = distributor;
	}

	public Integer getTotalBookings() {
		return totalBookings;
	}

	public void setTotalBookings(Integer totalBookings) {
		this.totalBookings = totalBookings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cusineType == null) ? 0 : cusineType.hashCode());
		result = prime * result + ((dateOfServing == null) ? 0 : dateOfServing.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		RecipeBean other = (RecipeBean) obj;
		if (cusineType == null) {
			if (other.cusineType != null)
				return false;
		} else if (!cusineType.equals(other.cusineType))
			return false;
		if (dateOfServing == null) {
			if (other.dateOfServing != null)
				return false;
		} else if (!dateOfServing.equals(other.dateOfServing))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecipeBean [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", totalServings=" + totalServings + ", category=" + category + ", cusineType=" + cusineType
				+ ", dateOfServing=" + dateOfServing + ", status=" + status + ", image=" + image + ", owner=" + owner
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", recipeDistributorStatus="
				+ recipeDistributorStatus + ", distributor=" + distributor + "]";
	}

}
