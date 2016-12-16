package com.foodquest.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.models.ImageBean;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.repository.IRecipeTypeRepository;
import com.foodquest.utils.lang.AppException;

public class RecipeTypeRepositoryImpl implements IRecipeTypeRepository {
	private static final Logger LOGGER = Logger.getLogger(RecipeTypeRepositoryImpl.class.getName());
	private static IRecipeTypeRepository INSATNCE;
	private DBConnectionManager dbConnectionManager;

	private RecipeTypeRepositoryImpl() {
		dbConnectionManager = DBConnectionManager.getInstance();
	}

	@Override
	public Integer createCategory(CategoryBean bean) throws AppException {
		System.out.println(bean);
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "insert into recipe_category (name, description, image_secure_url, image_url, image_public_id) values (?, ?,?,?,?)";
			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getDescription());
			pst.setString(3, bean.getImage().getSecureUrl());
			pst.setString(4, bean.getImage().getImageUrl());
			pst.setString(5, bean.getImage().getPublicId());
			pst.executeUpdate();

			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					bean.setId(rs.getInt(1));
				}
			}
			return bean.getId();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Integer createCusineType(CusineTypeBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "insert into cusine_type (name, description, image_secure_url, image_url, image_public_id) values (?, ?,?,?,?)";
			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getDescription());
			pst.setString(3, bean.getImage().getSecureUrl());
			pst.setString(4, bean.getImage().getImageUrl());
			pst.setString(5, bean.getImage().getPublicId());
			pst.executeUpdate();

			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					bean.setId(rs.getInt(1));
				}
			}
			return bean.getId();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public CategoryBean findCategoryById(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, description, image_secure_url, image_url, image_public_id from recipe_category where id= ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			return rs.next() != true ? null : formatResultSetCategory(rs);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public CategoryBean findCategoryByName(String name) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, description, image_secure_url, image_url, image_public_id from recipe_category where name= ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			return rs.next() != true ? null : formatResultSetCategory(rs);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public List<CategoryBean> findAllCategories() throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		List<CategoryBean> categories = new ArrayList<>();
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, description, image_secure_url, image_url, image_public_id from recipe_category";
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				categories.add(formatResultSetCategory(rs));
			}
			return categories;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public CusineTypeBean findCusineTypeById(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, description, image_secure_url, image_url, image_public_id from cusine_type where id= ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			return rs.next() != true ? null : formatResultSetCusine(rs);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public CusineTypeBean findCusineTypeByName(String name) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, description, image_secure_url, image_url, image_public_id from cusine_type where name= ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			return rs.next() != true ? null : formatResultSetCusine(rs);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public List<CusineTypeBean> findAllCusines() throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		List<CusineTypeBean> cusines = new ArrayList<>();
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, description, image_secure_url, image_url, image_public_id from cusine_type";
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				cusines.add(formatResultSetCusine(rs));
			}
			return cusines;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Boolean updateCategory(CategoryBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "update recipe_category set name = ?, description = ?, image_secure_url = ?, image_url = ?, image_public_id = ? where id = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getDescription());
			pst.setString(3, bean.getImage().getSecureUrl());
			pst.setString(4, bean.getImage().getImageUrl());
			pst.setString(5, bean.getImage().getPublicId());
			pst.setInt(6, bean.getId());
			return pst.executeUpdate() > 0;

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Boolean updateCusineType(CusineTypeBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "update cusine_type set name = ?, description = ?, image_secure_url = ?, image_url = ?, image_public_id = ?where id = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getDescription());
			pst.setString(3, bean.getImage().getSecureUrl());
			pst.setString(4, bean.getImage().getImageUrl());
			pst.setString(5, bean.getImage().getPublicId());
			pst.setInt(6, bean.getId());
			return pst.executeUpdate() > 0;

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Boolean deleteCategory(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "delete from recipe_category where id = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Boolean deleteCusineType(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "delete from cusine_type where id = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	CategoryBean formatResultSetCategory(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		CategoryBean bean = new CategoryBean();
		ImageBean image = new ImageBean();

		if (CommonDBUtil.findIfColumnExists("id", rs)) {
			bean.setId(rs.getInt("id"));
		}
		if (CommonDBUtil.findIfColumnExists("name", rs)) {
			bean.setName(rs.getString("name"));
		}
		if (CommonDBUtil.findIfColumnExists("description", rs)) {
			bean.setDescription(rs.getString("description"));
		}
		if (CommonDBUtil.findIfColumnExists("image_secure_url", rs)) {
			image.setSecureUrl(rs.getString("image_secure_url"));
		}
		if (CommonDBUtil.findIfColumnExists("image_url", rs)) {
			image.setImageUrl(rs.getString("image_url"));
		}
		if (CommonDBUtil.findIfColumnExists("image_public_id", rs)) {
			image.setPublicId(rs.getString("image_public_id"));
		}
		bean.setImage(image);
		return bean;
	}

	CusineTypeBean formatResultSetCusine(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		CusineTypeBean bean = new CusineTypeBean();
		ImageBean image = new ImageBean();

		if (CommonDBUtil.findIfColumnExists("id", rs)) {
			bean.setId(rs.getInt("id"));
		}
		if (CommonDBUtil.findIfColumnExists("name", rs)) {
			bean.setName(rs.getString("name"));
		}
		if (CommonDBUtil.findIfColumnExists("description", rs)) {
			bean.setDescription(rs.getString("description"));
		}
		if (CommonDBUtil.findIfColumnExists("image_secure_url", rs)) {
			image.setSecureUrl(rs.getString("image_secure_url"));
		}
		if (CommonDBUtil.findIfColumnExists("image_url", rs)) {
			image.setImageUrl(rs.getString("image_url"));
		}
		if (CommonDBUtil.findIfColumnExists("image_public_id", rs)) {
			image.setPublicId(rs.getString("image_public_id"));
		}
		bean.setImage(image);
		return bean;
	}

	public static IRecipeTypeRepository getInstance() {
		if (INSATNCE == null) {
			synchronized (RecipeTypeRepositoryImpl.class) {
				INSATNCE = new RecipeTypeRepositoryImpl();
			}
		}
		return INSATNCE;
	}
}
