package com.foodquest.utils;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.models.ImageBean;

public class RecipeTypeXmlHandler extends DefaultHandler {

	private List<CategoryBean> categoryList;
	private CategoryBean category;
	private ImageBean image = new ImageBean();
	private List<CusineTypeBean> cusineList;
	private CusineTypeBean cusine;
	private boolean iscategory = false;
	private boolean iscusine = false;
	private boolean isName = false;
	private boolean isDescription = false;
	private boolean isImageSecureUrl = false;
	private boolean isImageUrl = false;
	private boolean isPublicId = false;

	public void startElement(String uri, String localName, String element, Attributes attributes) throws SAXException {
		if (element.equalsIgnoreCase("category")) {
			category = new CategoryBean();
			if (categoryList == null)
				categoryList = new ArrayList<>();
			iscategory = true;
		} else if (element.equalsIgnoreCase("cusine")) {
			cusine = new CusineTypeBean();
			if (cusineList == null)
				cusineList = new ArrayList<>();
			iscusine = true;
		} else if (element.equalsIgnoreCase("name")) {
			isName = true;
		} else if (element.equalsIgnoreCase("description")) {
			isDescription = true;
		} else if (element.equalsIgnoreCase("image_secure_url")) {
			isImageSecureUrl = true;
		} else if (element.equalsIgnoreCase("image_url")) {
			isImageUrl = true;
		} else if (element.equalsIgnoreCase("image_public_id")) {
			isPublicId = true;
		}
	}

	public void endElement(String uri, String localName, String element) throws SAXException {
		if (element.equalsIgnoreCase("category")) {
			categoryList.add(category);
			iscategory = false;
		}
		if (element.equalsIgnoreCase("cusine")) {
			cusineList.add(cusine);
			iscusine = false;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		if (isName) {
			if (iscategory) {
				category.setName(new String(ch, start, length));
			} else if (iscusine) {
				cusine.setName(new String(ch, start, length));
			}
			isName = false;
		} else if (isDescription) {
			if (iscategory) {
				category.setDescription(new String(ch, start, length));
			} else if (iscusine) {
				cusine.setDescription(new String(ch, start, length));
			}
			isDescription = false;
		} else if (isImageSecureUrl) {
			if (iscategory) {
				image.setSecureUrl(new String(ch, start, length));
			} else if (iscusine) {
				image.setSecureUrl(new String(ch, start, length));
			}
			isImageSecureUrl = false;
		} else if (isImageUrl) {
			if (iscategory) {
				image.setImageUrl(new String(ch, start, length));
			} else if (iscusine) {
				image.setImageUrl(new String(ch, start, length));
			}
			isImageUrl = false;
		} else if (isPublicId) {
			if (iscategory) {
				image.setPublicId(new String(ch, start, length));
				category.setImage(image);
			} else if (iscusine) {
				image.setPublicId(new String(ch, start, length));
				cusine.setImage(image);
			}
			isPublicId = false;
		}	
	}
	public List<CategoryBean> getCategoryList() {
		return categoryList;
	}

	public List<CusineTypeBean> getCusineList() {
		return cusineList;
	}

}
