package com.foodquest.services.impl;

import java.util.List;

import com.foodquest.models.DistributorBean;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeDistributorStatus;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.repository.IRecipeRepository;
import com.foodquest.repository.impl.RecipeRepositoryImpl;
import com.foodquest.services.IDistributorManager;
import com.foodquest.services.IRecipeManager;
import com.foodquest.utils.CommonUtil;
import com.foodquest.utils.lang.AppException;

public class RecipeManagerImpl implements IRecipeManager {
	private static IRecipeManager INSTANCE = null;
	private IRecipeRepository recipeRepository;
	private IDistributorManager distributorManager;

	private RecipeManagerImpl() {
		recipeRepository = RecipeRepositoryImpl.getInstance();
		distributorManager = DistributorManagerImpl.getInstance();
	}

	@Override
	public Integer create(RecipeBean bean) throws AppException {
		bean.setStatus(RecipeStatus.PENDING);
		bean.setRecipeDistributorStatus(RecipeDistributorStatus.UNASSIGNED);
		bean.setCreatedAt(CommonUtil.getCurrentTimeInMillis());
		bean.setUpdatedAt(CommonUtil.getCurrentTimeInMillis());
		System.out.println("Creating Recipe "+bean);
		return recipeRepository.create(bean);
	}

	@Override
	public RecipeBean findById(Integer id) throws AppException {
		return recipeRepository.findById(id);
	}

	@Override
	public List<RecipeBean> findAll(RecipeStatus status, String serachKey) throws AppException {
		return recipeRepository.findAll(status, serachKey);
	}
	
	@Override
	public List<RecipeBean> findAll(List<RecipeStatus> statuses, String serachKey) throws AppException {
		return recipeRepository.findAll(statuses, serachKey);
	}

	@Override
	public Boolean update(RecipeBean bean) throws AppException {
		RecipeBean _bean = findById(bean.getId());
		if (bean.getCategory() != null) {
			_bean.setCategory(bean.getCategory());
		}
		if (bean.getCusineType() != null) {
			_bean.setCusineType(bean.getCusineType());
		}
		if (bean.getDateOfServing() != null) {
			_bean.setDateOfServing(bean.getDateOfServing());
		}
		if (bean.getDescription() != null) {
			_bean.setDescription(bean.getDescription());
		}
		if (bean.getImage() != null) {
			_bean.setImage(bean.getImage());
		}
		if (bean.getName() != null) {
			_bean.setName(bean.getName());
		}
		if (bean.getPrice() != null) {
			_bean.setPrice(bean.getPrice());
		}
		if (bean.getStatus() != null) {
			_bean.setStatus(bean.getStatus());
		}
		if (bean.getTotalServings() != null) {
			_bean.setTotalServings(bean.getTotalServings());
		}
		if (bean.getDistributor() != null) {
			_bean.setDistributor(bean.getDistributor());
		}
		if (bean.getRecipeDistributorStatus() != null) {
			_bean.setRecipeDistributorStatus(bean.getRecipeDistributorStatus());
//			if (bean.getRecipeDistributorStatus().equals(RecipeDistributorStatus.DISPATCHED)) {
//			s	_bean.setStatus(RecipeStatus.COMPLETED);
//				_bean.getDistributor().decrementTotalOrdersToBeDelivered();
//				bean.getDistributor().incrementTotalOrdersDelivered();
//				distributorManager.update(bean.getDistributor());
//			}
		}
		_bean.setUpdatedAt(CommonUtil.getCurrentTimeInMillis());
		System.out.println("To update "+_bean);
		return recipeRepository.update(_bean);
	}

	@Override
	public Boolean assignDistributor(RecipeBean bean, double radius) throws AppException {
//		if (bean.getStatus() == null || !bean.getStatus().equals(RecipeStatus.PUBLISHED)
//				|| !bean.getRecipeDistributorStatus().equals(RecipeDistributorStatus.UNASSIGNED)) {
//			throw new AppException("Cannot assign distributor");
//
//		}
		List<DistributorBean> distributors = distributorManager.searchDistributors(bean.getOwner().getLocation(),
				radius);
		if (distributors.size() == 0) {
			throw new AppException("No distributors found");
		}
		boolean isUpdated = false;
		DistributorBean distributor = distributors.get(0);
		bean.setDistributor(distributor);
		bean.setRecipeDistributorStatus(RecipeDistributorStatus.ASSIGNED);
		isUpdated = update(bean);
		distributor.incrementTotalOrdersToBeDelivered();
		distributorManager.update(distributor);
		return isUpdated;
	}

	@Override
	public Boolean delete(Integer id) throws AppException {
		return recipeRepository.delete(id);
	}

	public static IRecipeManager getInstance() {
		synchronized (RecipeManagerImpl.class) {
			if (INSTANCE == null) {

				INSTANCE = new RecipeManagerImpl();
			}
		}
		return INSTANCE;
	}

}
