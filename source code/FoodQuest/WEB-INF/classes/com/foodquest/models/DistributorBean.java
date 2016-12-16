package com.foodquest.models;

/**
 * DistributorBean is a POJO for storing distributor information
 *
 * @author Shreyas K
 */
public class DistributorBean {
	private Integer id;
	private String name;
	private String websiteUrl;
	private ImageBean image;
	private String phone;
	private LocationBean location;
	private DistributorStatus status;
	private Integer totalOrdersDelivered;
	private Integer totalOrdersToBeDelivered;

	public enum DistributorStatus {
		PENDING, VERIFIED, DISABLED
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

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public ImageBean getImage() {
		return image;
	}

	public void setImage(ImageBean image) {
		this.image = image;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocationBean getLocation() {
		return location;
	}

	public void setLocation(LocationBean location) {
		this.location = location;
	}

	public DistributorStatus getStatus() {
		return status;
	}

	public void setStatus(DistributorStatus status) {
		this.status = status;
	}

	public Integer getTotalOrdersDelivered() {
		return totalOrdersDelivered;
	}

	public void setTotalOrdersDelivered(Integer totalOrdersDelivered) {
		this.totalOrdersDelivered = totalOrdersDelivered;
	}

	public Integer getTotalOrdersToBeDelivered() {
		return totalOrdersToBeDelivered;
	}

	public void setTotalOrdersToBeDelivered(Integer totalOrdersToBeDelivered) {
		this.totalOrdersToBeDelivered = totalOrdersToBeDelivered;
	}

	public void incrementTotalOrdersToBeDelivered() {
		this.totalOrdersToBeDelivered += 1;
	}

	public void decrementTotalOrdersToBeDelivered() {
		this.totalOrdersToBeDelivered -= 1;
	}

	public void incrementTotalOrdersDelivered() {
		this.totalOrdersDelivered += 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DistributorBean other = (DistributorBean) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DistributorBean [id=" + id + ", name=" + name + ", websiteUrl=" + websiteUrl + ", image=" + image
				+ ", phone=" + phone + ", location=" + location + ", status=" + status + ", totalOrdersDelivered="
				+ totalOrdersDelivered + ", totalOrdersToBeDelivered=" + totalOrdersToBeDelivered + "]";
	}

}
