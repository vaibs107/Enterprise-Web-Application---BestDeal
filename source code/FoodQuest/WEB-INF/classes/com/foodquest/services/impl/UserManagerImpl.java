package com.foodquest.services.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.AccountStatus;
import com.foodquest.repository.IUserRepository;
import com.foodquest.repository.impl.UserRepositoryImpl;
import com.foodquest.services.IUserManager;
import com.foodquest.utils.CommonUtil;
import com.foodquest.utils.GeoCodeUtil;
import com.foodquest.utils.lang.AppException;

public class UserManagerImpl implements IUserManager {
	private IUserRepository userRepository;
	private static IUserManager userManager = null;

	private UserManagerImpl() {
		userRepository = UserRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(UserBean bean) throws AppException {
		String ePassword = encrypt(bean.getPassword());
		GeoCodeUtil.doCalculateLatLon(bean.getLocation());
		System.out.println();
		bean.setPassword(ePassword);
		bean.setCreatedAt(CommonUtil.getCurrentTimeInMillis());
		bean.setUpdatedAt(CommonUtil.getCurrentTimeInMillis());
		bean.setStatus(AccountStatus.ACTIVE);
		return userRepository.create(bean);
	}

	@Override
	public UserBean findById(Integer id) throws AppException {
		return userRepository.findById(id);
	}

	@Override
	public UserBean findByEmail(String email) throws AppException {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserBean> findAll(AccountStatus status) throws AppException {
		return userRepository.findAll(status);
	}

	@Override
	public Boolean update(UserBean bean) throws AppException {
		UserBean _bean = findById(bean.getId());
		if (bean.getFirstName() != null) {
			_bean.setFirstName(bean.getFirstName());
		}
		if (bean.getLastName() != null) {
			_bean.setLastName(bean.getLastName());
		}
		if (bean.getPassword() != null) {
			_bean.setPassword(encrypt(bean.getPassword()));
		}
		if (bean.getPhone() != null) {
			_bean.setPhone(bean.getPhone());
		}
		if (bean.getEmail() != null) {
			_bean.setEmail(bean.getEmail());
		}
		if (bean.getImage() != null) {
			_bean.setImage(bean.getImage());
		}
		if (bean.getLocation() != null) {
			GeoCodeUtil.doCalculateLatLon(bean.getLocation());
			_bean.setLocation(bean.getLocation());
		}
		if (bean.getStatus() != null) {
			_bean.setStatus(bean.getStatus());
		}
		_bean.setUpdatedAt(CommonUtil.getCurrentTimeInMillis());
		return userRepository.update(_bean);
	}

	@Override
	public Boolean delete(Integer id) throws AppException {
		return userRepository.delete(id);
	}

	@Override
	public UserBean authenticateByEmail(String email, String password) throws AppException {
		UserBean bean = userRepository.findByEmail(email);
		if (bean != null) {
			boolean pwdOK = BCrypt.checkpw(password, bean.getPassword());
			if (pwdOK) {
				bean.setPassword(null);
				return bean;
			}
		}
		return null;
	}

	private static String encrypt(String str) {
		return (str != null) ? BCrypt.hashpw(str, BCrypt.gensalt()) : null;
	}

	public static IUserManager getInstance() {
		synchronized (UserManagerImpl.class) {
			if (userManager == null) {
				userManager = new UserManagerImpl();
			}
		}
		return userManager;
	}
}
