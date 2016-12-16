package com.foodquest.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.foodquest.models.ImageBean;

/**
 * CloudinaryUtil helps to upload files on Cloudinary platform.
 *
 * @author Shreyas K
 */
public class CloudinaryUtil {
	private static CloudinaryUtil cloudinaryUtil;
	private static String CLOUDINARY_URL;
	Cloudinary cloudinary;

	private CloudinaryUtil() {
		initConfig();
	}

	public void initConfig() {
		CLOUDINARY_URL = PropertiesStore.getInstance().getProperty("CLOUDINARY_URL");
		cloudinary = new Cloudinary(CLOUDINARY_URL);
	}

	public ImageBean uploadFile(File file) throws IOException {
		Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		return getImage(uploadResult);
	}

	private ImageBean getImage(Map map) {
		ImageBean imageBean = new ImageBean();
		imageBean.setImageUrl(map.get("url").toString());
		imageBean.setSecureUrl(map.get("secure_url").toString());
		imageBean.setPublicId(map.get("public_id").toString());
		return imageBean;
	}

	public static CloudinaryUtil getInstance() {
		synchronized (CloudinaryUtil.class) {
			if (cloudinaryUtil == null) {
				cloudinaryUtil = new CloudinaryUtil();
			}
		}
		return cloudinaryUtil;
	}

	public static void main(String[] args) throws IOException {
		CloudinaryUtil util = CloudinaryUtil.getInstance();
		System.out.println(util.uploadFile(new File("/home/shreyas/Pictures/1.png")));

	}
}
