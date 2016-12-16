package com.foodquest.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foodquest.models.ImageBean;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeDistributorStatus;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.repository.IDistributorRepository;
import com.foodquest.repository.IRecipeRepository;
import com.foodquest.repository.IRecipeTypeRepository;
import com.foodquest.repository.IUserRepository;
import com.foodquest.utils.lang.AppException;

public class RecipeRepositoryImpl implements IRecipeRepository {
	private static final Logger LOGGER = Logger.getLogger(RecipeRepositoryImpl.class.getName());
	private static IRecipeRepository INSTANCE = null;
	private DBConnectionManager dbConnectionManager;
	private IRecipeTypeRepository recipeTypeRepository;
	private IUserRepository userRepository;
	private IDistributorRepository distributorRepository;

	private RecipeRepositoryImpl() {
		dbConnectionManager = DBConnectionManager.getInstance();
		recipeTypeRepository = RecipeTypeRepositoryImpl.getInstance();
		userRepository = UserRepositoryImpl.getInstance();
		distributorRepository = DistributorRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(RecipeBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {

			conn = dbConnectionManager.getConnection();
			String query = "insert into recipe (name, category_id, cusine_type_id, description, price, total_servings, created_at, updated_at, status, "
					+ "date_of_serving, user_id, image_secure_url, image_url, image_public_id,distributor_status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getName());
			pst.setInt(2, bean.getCategory().getId());
			pst.setInt(3, bean.getCusineType().getId());
			pst.setString(4, bean.getDescription());
			pst.setDouble(5, bean.getPrice());
			pst.setInt(6, bean.getTotalServings());
			pst.setLong(7, bean.getCreatedAt());
			pst.setLong(8, bean.getUpdatedAt());
			pst.setString(9, bean.getStatus().name());
			pst.setLong(10, bean.getDateOfServing());
			pst.setInt(11, bean.getOwner().getId());
			pst.setString(12, bean.getImage().getSecureUrl());
			pst.setString(13, bean.getImage().getImageUrl());
			pst.setString(14, bean.getImage().getPublicId());
			pst.setString(15, bean.getRecipeDistributorStatus().name());
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
	public RecipeBean findById(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		RecipeBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(
					"select id, name, category_id, cusine_type_id, description, price, total_servings, total_bookings, created_at, updated_at, status, "
							+ "date_of_serving, user_id, image_secure_url, image_url, image_public_id,distributor_status from recipe where id  = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			bean = rs.next() != true ? null : formatResultSet(rs);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
		return bean;
	}

	@Override
	public List<RecipeBean> findAll(RecipeStatus status, String searchKey) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		List<RecipeBean> recipes = new ArrayList<RecipeBean>();
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, category_id, cusine_type_id, description, price, total_servings, total_bookings, created_at, updated_at, status, date_of_serving, "
					+ "user_id, image_secure_url, image_url, image_public_id, distributor_id, distributor_status from recipe CONDITION order by date_of_serving desc";
			boolean includeSearchQuery = false;
			String searchQuery = "";
			StringBuilder statusQueryBuilder = new StringBuilder("where status = ");
			if (status != null) {
				includeSearchQuery = true;
				statusQueryBuilder.append("'" + status.name() + "'");
			}
			if (searchKey != null && !searchKey.isEmpty()) {
				if (includeSearchQuery) {
					searchQuery = " AND name LIKE '" + searchKey + "%'";
				} else {
					searchQuery = " where name LIKE %'" + searchKey + "'";
				}
			}
			String contionalQuery = "";
			if (status != null) {
				contionalQuery = statusQueryBuilder.append(searchQuery).toString();
			} else {
				contionalQuery = searchQuery;
			}
			query = query.replace("CONDITION", contionalQuery);
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				recipes.add(formatResultSet(rs));
			}
			return recipes;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public List<RecipeBean> findAll(List<RecipeStatus> statuses, String serachKey) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		List<RecipeBean> recipes = new ArrayList<RecipeBean>();
		try {
			conn = dbConnectionManager.getConnection();
			String query = "select id, name, category_id, cusine_type_id, description, price, total_servings, total_bookings, created_at, updated_at, status, date_of_serving, "
					+ "user_id, image_secure_url, image_url, image_public_id, distributor_id, distributor_status from recipe CONDITION order by date_of_serving desc";
			boolean includeSearchQuery = false;
			String searchQuery = "";
			StringBuilder statusQueryBuilder = new StringBuilder("where status in (");
			if (statuses != null) {
				includeSearchQuery = true;
				for (int i = 0; i < statuses.size(); i++) {
					statusQueryBuilder.append("'" + statuses.get(i).name() + "'");
					if (i != statuses.size() - 1) {
						statusQueryBuilder.append(", ");
					}
				}
			}
			statusQueryBuilder.append(")");
			if (serachKey != null && !serachKey.isEmpty()) {
				if (includeSearchQuery) {
					searchQuery = " AND name LIKE '" + serachKey + "%'";
				} else {
					searchQuery = " where name LIKE %'" + serachKey + "'";
				}
			}
			String contionalQuery = "";
			if (statuses != null) {
				contionalQuery = statusQueryBuilder.append(searchQuery).toString();
			} else {
				contionalQuery = searchQuery;
			}
			query = query.replace("CONDITION", contionalQuery);
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				recipes.add(formatResultSet(rs));
			}
			return recipes;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Boolean update(RecipeBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {

			conn = dbConnectionManager.getConnection();
			String query = "update recipe  set name = ?, category_id = ?, cusine_type_id = ?, description = ?, price = ?, total_servings = ?, created_at = ?, updated_at = ?, status = ?, "
					+ "date_of_serving = ?, user_id = ?, image_secure_url = ?, image_url = ?, image_public_id = ?, distributor_id = ?, distributor_status =?, total_bookings = ?  where id = ?";
			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getName());
			pst.setInt(2, bean.getCategory().getId());
			pst.setInt(3, bean.getCusineType().getId());
			pst.setString(4, bean.getDescription());
			pst.setDouble(5, bean.getPrice());
			pst.setInt(6, bean.getTotalServings());
			pst.setLong(7, bean.getCreatedAt());
			pst.setLong(8, bean.getUpdatedAt());
			pst.setString(9, bean.getStatus().name());
			pst.setLong(10, bean.getDateOfServing());
			pst.setInt(11, bean.getOwner().getId());
			pst.setString(12, bean.getImage().getSecureUrl());
			pst.setString(13, bean.getImage().getImageUrl());
			pst.setString(14, bean.getImage().getPublicId());
			if (bean.getDistributor() != null) {
				pst.setInt(15, bean.getDistributor().getId());
			} else {
				pst.setNull(15, java.sql.Types.INTEGER);
			}
			pst.setString(16, bean.getRecipeDistributorStatus().name());
			if (bean.getTotalBookings() != null) {
				pst.setInt(17, bean.getTotalBookings());
			} else {
				pst.setNull(17, java.sql.Types.INTEGER);
			}
			pst.setInt(18, bean.getId());
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
	public Boolean delete(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			String query = "delete from recipe where id = ?";
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

	private String getSelectQuery(Set<String> clauses) {
		String query = "select id, name, category_id, cusine_type_id, description, price, total_servings, total_bookings, created_at, updated_at, status, date_of_serving, "
				+ "user_id, image_secure_url, image_url, image_public_id, distributor_id, distributor_status from recipe CONDITION order by date_of_serving desc";
		StringBuilder builder = new StringBuilder();
		boolean isFirst = false;
		if (clauses.size() > 0) {
			for (String clause : clauses) {
				if (clause.equalsIgnoreCase("name")) {
					clause = clause + " LIKE ?";
				} else {
					clause = clause + " = ?";
				}
				if (!isFirst) {
					builder.append(" where " + clause);
					isFirst = true;
				} else {
					builder.append(" and " + clause);
				}
			}
		} else {
			builder.append("");
		}
		query = query.replace("CONDITION", builder.toString());
		return query;
	}

	private PreparedStatement setParam(int index, Object value, Class type, PreparedStatement pst) throws SQLException {
		if (type.equals(Integer.class)) {
			pst.setInt(index, (int) value);
		} else if (type.equals(String.class)) {
			pst.setString(index, (String) value);
		} else if (type.equals(Double.class)) {
			pst.setDouble(index, (double) value);
		} else if (type.equals(Long.class)) {
			pst.setLong(index, (long) value);
		}
		return pst;
	}

	private RecipeBean formatResultSet(ResultSet rs) throws SQLException, AppException {
		if (rs == null) {
			return null;
		}
		RecipeBean bean = new RecipeBean();
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
		if (CommonDBUtil.findIfColumnExists("price", rs)) {
			bean.setPrice(rs.getDouble("price"));
		}
		if (CommonDBUtil.findIfColumnExists("total_servings", rs)) {
			bean.setTotalServings(rs.getInt("total_servings"));
		}
		if (CommonDBUtil.findIfColumnExists("total_bookings", rs)) {
			bean.setTotalBookings(rs.getInt("total_bookings"));
		}
		if (CommonDBUtil.findIfColumnExists("created_at", rs)) {
			bean.setCreatedAt(rs.getLong("created_at"));
		}
		if (CommonDBUtil.findIfColumnExists("updated_at", rs)) {
			bean.setUpdatedAt(rs.getLong("updated_at"));
		}
		if (CommonDBUtil.findIfColumnExists("status", rs)) {
			bean.setStatus(RecipeStatus.valueOf(rs.getString("status")));
		}
		if (CommonDBUtil.findIfColumnExists("date_of_serving", rs)) {
			bean.setDateOfServing(rs.getLong("date_of_serving"));
		}
		if (CommonDBUtil.findIfColumnExists("category_id", rs)) {
			bean.setCategory(recipeTypeRepository.findCategoryById(rs.getInt("category_id")));
		}
		if (CommonDBUtil.findIfColumnExists("cusine_type_id", rs)) {
			bean.setCusineType(recipeTypeRepository.findCusineTypeById(rs.getInt("cusine_type_id")));
		}
		if (CommonDBUtil.findIfColumnExists("user_id", rs)) {
			bean.setOwner(userRepository.findById(rs.getInt("user_id")));
		}
		if (CommonDBUtil.findIfColumnExists("distributor_id", rs)) {
			bean.setDistributor(distributorRepository.findById(rs.getInt("distributor_id")));
		}
		if (CommonDBUtil.findIfColumnExists("distributor_status", rs)) {
			bean.setRecipeDistributorStatus(RecipeDistributorStatus.valueOf(rs.getString("distributor_status")));
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

	public static IRecipeRepository getInstance() {
		if (INSTANCE == null) {
			synchronized (RecipeRepositoryImpl.class) {
				INSTANCE = new RecipeRepositoryImpl();
			}
		}
		return INSTANCE;
	}
}
