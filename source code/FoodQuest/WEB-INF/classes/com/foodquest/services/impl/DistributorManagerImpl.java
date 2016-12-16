package com.foodquest.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.foodquest.models.DistributorBean;
import com.foodquest.models.DistributorBean.DistributorStatus;
import com.foodquest.models.LocationBean;
import com.foodquest.models.Pair;
import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.UserRole;
import com.foodquest.repository.IDistributorRepository;
import com.foodquest.repository.IGeoLocationRepository;
import com.foodquest.repository.impl.DistributorRepositoryImpl;
import com.foodquest.repository.impl.GeoLocationRepositoryImpl;
import com.foodquest.services.IDistributorManager;
import com.foodquest.services.IUserManager;
import com.foodquest.utils.GeoCodeUtil;
import com.foodquest.utils.lang.AppException;

public class DistributorManagerImpl implements IDistributorManager {
	private static IDistributorManager INSTANCE = null;
	private IDistributorRepository distributorRepository;
	private IUserManager userManager;
	private IGeoLocationRepository geoLocationRepository;

	public DistributorManagerImpl() {
		distributorRepository = DistributorRepositoryImpl.getInstance();
		userManager = UserManagerImpl.getInstance();
		geoLocationRepository = GeoLocationRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(DistributorBean bean) throws AppException {
		if (distributorRepository.findByName(bean.getName()) != null) {
			throw new AppException("Distributor name already taken");
		}
//		bean.setStatus(DistributorStatus.PENDING);
		bean.setStatus(DistributorStatus.VERIFIED);
		GeoCodeUtil.doCalculateLatLon(bean.getLocation());
		return distributorRepository.create(bean);
	}

	@Override
	public DistributorBean findById(Integer id) throws AppException {
		return distributorRepository.findById(id);
	}

	@Override
	public DistributorBean findByName(String name) throws AppException {
		return distributorRepository.findByName(name);
	}

	@Override
	public List<DistributorBean> findAll(DistributorStatus status, String name) throws AppException {
		return distributorRepository.findAll(status, name);
	}

	@Override
	public Boolean update(DistributorBean bean) throws AppException {
		DistributorBean _bean = findById(bean.getId());
		if (_bean == null) {
			return false;
		}
		if (bean.getImage() != null) {
			_bean.setImage(bean.getImage());
		}
		if (bean.getLocation() != null) {
			GeoCodeUtil.doCalculateLatLon(bean.getLocation());
			_bean.setLocation(bean.getLocation());
		}
		if (bean.getName() != null) {
			_bean.setName(bean.getName());
		}
		if (bean.getPhone() != null) {
			_bean.setPhone(bean.getPhone());
		}
		if (bean.getStatus() != null) {
			_bean.setStatus(bean.getStatus());
		}
		if (bean.getTotalOrdersDelivered() != null) {
			_bean.setTotalOrdersDelivered(bean.getTotalOrdersDelivered());
		}
		if (bean.getTotalOrdersToBeDelivered() != null) {
			_bean.setTotalOrdersToBeDelivered(bean.getTotalOrdersToBeDelivered());
		}
		if (bean.getWebsiteUrl() != null) {
			_bean.setWebsiteUrl(bean.getWebsiteUrl());
		}
		return distributorRepository.update(_bean);
	}

	@Override
	public Boolean delete(Integer id) throws AppException {
		return distributorRepository.delete(id);
	}

	@Override
	public List<UserBean> findDitributorUsers(Integer id) throws AppException {
		List<UserBean> users = new ArrayList<>();
		List<Integer> ids = distributorRepository.getUserIds(id);
		if (ids.size() == 0) {
			return users;
		}
		for (Integer uid : ids) {
			UserBean bean = userManager.findById(uid);
			if (bean != null) {
				users.add(bean);
			}
		}
		return users;
	}

	@Override
	public Pair<Integer, Integer> createDistributorAndUser(DistributorBean distributor, UserBean user)
			throws AppException {
		DistributorBean bean = distributorRepository.findByName(distributor.getName());
		boolean isDistributorExists = false, isPrimaryDistributor = false;
		if (bean != null) {
			isDistributorExists = true;
		}
		if (!isDistributorExists) {
			bean = distributor;
			bean.setId(create(bean));
		}
		isPrimaryDistributor = distributorRepository.getUserIds(bean.getId()).size() > 0 ? false : true;
		if (isPrimaryDistributor) {
			user.setRole(UserRole.PRIMARY_DISTRIBUTOR);
		} else {
			user.setRole(UserRole.SECONDARY_DISTRIBUTOR);
		}
		user.setLocation(bean.getLocation());
		user.setId(userManager.create(user));
		distributorRepository.createUserDistributorMapping(bean.getId(), user.getId());
		return new Pair<Integer, Integer>(bean.getId(), user.getId());
	}

	@Override
	public List<DistributorBean> searchDistributors(LocationBean location, double radius) throws AppException {
		List<DistributorBean> distributors = new ArrayList<>();
		List<LocationBean> locations = geoLocationRepository.search(location, radius);
		if (locations.size() == 0) {
			return distributors;
		}
		for (LocationBean loc : locations) {
			DistributorBean distributor = distributorRepository.findByLocation(loc);
			if (distributor != null) {
				distributors.add(distributor);
			}
		}
		return distributors;
	}

	public static IDistributorManager getInstance() {
		synchronized (DistributorManagerImpl.class) {
			if (INSTANCE == null) {
				INSTANCE = new DistributorManagerImpl();
			}
		}
		return INSTANCE;
	}
}
