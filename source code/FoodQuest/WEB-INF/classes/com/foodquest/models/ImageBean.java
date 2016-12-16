package com.foodquest.models;

import java.io.Serializable;

/**
* ImageBean is a POJO for storing image information
*
* @author  Shreyas K
*/
public class ImageBean implements Serializable{
	private static final long serialVersionUID = 5L;
	private String imageUrl;
	private String secureUrl;
	private String publicId;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSecureUrl() {
		return secureUrl;
	}

	public void setSecureUrl(String secureUrl) {
		this.secureUrl = secureUrl;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result
				+ ((publicId == null) ? 0 : publicId.hashCode());
		result = prime * result
				+ ((secureUrl == null) ? 0 : secureUrl.hashCode());
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
		ImageBean other = (ImageBean) obj;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (publicId == null) {
			if (other.publicId != null)
				return false;
		} else if (!publicId.equals(other.publicId))
			return false;
		if (secureUrl == null) {
			if (other.secureUrl != null)
				return false;
		} else if (!secureUrl.equals(other.secureUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageBean [imageUrl=" + imageUrl + ", secureUrl=" + secureUrl
				+ ", publicId=" + publicId + "]";
	}

}
