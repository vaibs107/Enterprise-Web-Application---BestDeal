package com.foodquest.services.impl;

import java.util.List;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.repository.IRecipeTypeRepository;
import com.foodquest.repository.impl.RecipeTypeRepositoryImpl;
import com.foodquest.services.IRecipeTypeManager;
import com.foodquest.utils.lang.AppException;

public class RecipeTypeManagerImpl implements IRecipeTypeManager {
	private static IRecipeTypeManager INSTANCE = null;
	private IRecipeTypeRepository recipeTypeRepository;

	private RecipeTypeManagerImpl() {
		recipeTypeRepository = RecipeTypeRepositoryImpl.getInstance();
	}

	@Override
	public Integer createCategory(CategoryBean bean) throws AppException {
		return recipeTypeRepository.createCategory(bean);
	}

	@Override
	public Integer createCusineType(CusineTypeBean bean) throws AppException {
		return recipeTypeRepository.createCusineType(bean);
	}

	@Override
	public CategoryBean findCategoryById(Integer id) throws AppException {
		return recipeTypeRepository.findCategoryById(id);
	}

	@Override
	public CategoryBean findCategoryByName(String name) throws AppException {
		return recipeTypeRepository.findCategoryByName(name);
	}
	
	@Override
	public List<CategoryBean> findAllCategories() throws AppException {
		return recipeTypeRepository.findAllCategories();
	}
	@Override
	public CusineTypeBean findCusineTypeById(Integer id) throws AppException {
		return recipeTypeRepository.findCusineTypeById(id);
	}

	@Override
	public CusineTypeBean findCusineTypeByName(String name) throws AppException {
		return recipeTypeRepository.findCusineTypeByName(name);
	}
	
	@Override
	public List<CusineTypeBean> findAllCusines() throws AppException {
		return recipeTypeRepository.findAllCusines();
	}

	@Override
	public Boolean updateCategory(CategoryBean bean) throws AppException {
		CategoryBean _bean = findCategoryById(bean.getId());
		if (_bean == null) {
			return false;
		}
		if (bean.getName() != null) {
			_bean.setName(bean.getName());
		}
		if (bean.getDescription() != null) {
			_bean.setDescription(bean.getDescription());
		}
		if (bean.getImage() != null) {
			_bean.setImage(bean.getImage());
		}
		return recipeTypeRepository.updateCategory(_bean);
	}

	@Override
	public Boolean updateCusineType(CusineTypeBean bean) throws AppException {
		CusineTypeBean _bean = findCusineTypeById(bean.getId());
		if (_bean == null) {
			return false;
		}
		if (bean.getName() != null) {
			_bean.setName(bean.getName());
		}
		if (bean.getDescription() != null) {
			_bean.setDescription(bean.getDescription());
		}
		if (bean.getImage() != null) {
			_bean.setImage(bean.getImage());
		}
		return recipeTypeRepository.updateCusineType(_bean);
	}

	@Override
	public Boolean deleteCategory(Integer id) throws AppException {
		return recipeTypeRepository.deleteCategory(id);
	}

	@Override
	public Boolean deleteCusineType(Integer id) throws AppException {
		return recipeTypeRepository.deleteCusineType(id);
	}

	public static IRecipeTypeManager getInstance() {
		synchronized (RecipeTypeManagerImpl.class) {
			if (INSTANCE == null) {

				INSTANCE = new RecipeTypeManagerImpl();
			}
		}
		return INSTANCE;
	}
}
