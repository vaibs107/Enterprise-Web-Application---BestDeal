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

import com.foodquest.models.ImageBean;
import com.foodquest.models.LocationBean;
import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.AccountStatus;
import com.foodquest.models.UserBean.Gender;
import com.foodquest.models.UserBean.UserRole;
import com.foodquest.models.UserBean.UserType;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.repository.ILocationRepository;
import com.foodquest.repository.IUserRepository;
import com.foodquest.utils.CommonUtil;
import com.foodquest.utils.lang.AppException;

public class UserRepositoryImpl implements IUserRepository {
	private static final Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class.getName());
	private DBConnectionManager dbConnectionManager;
	private static IUserRepository userRepository;
	private ILocationRepository locationRepository;

	private UserRepositoryImpl() {
		dbConnectionManager = DBConnectionManager.getInstance();
		locationRepository = LocationRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(UserBean bean) throws AppException {
		LOGGER.info("Create user :: " + bean);
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			if (bean.getLocation().getId() == null) {
				bean.getLocation().setId(locationRepository.create(bean.getLocation()));
			}
			String userInsertionQuery = "insert into user (first_name, last_name, email, password, phone, status, created_at, updated_at,"
					+ " location_id, image_secure_url, image_url, image_public_id, role, type, gender) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			pst = conn.prepareStatement(userInsertionQuery, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getFirstName());
			pst.setString(2, bean.getLastName());
			pst.setString(3, bean.getEmail());
			pst.setString(4, bean.getPassword());
			pst.setString(5, bean.getPhone());
			pst.setString(6, bean.getStatus().name());
			pst.setLong(7, bean.getCreatedAt());
			pst.setLong(8, bean.getUpdatedAt());
			pst.setInt(9, bean.getLocation().getId());
			pst.setString(10, bean.getImage().getImageUrl());
			pst.setString(11, bean.getImage().getSecureUrl());
			pst.setString(12, bean.getImage().getPublicId());
			if (bean.getRole() != null) {
				pst.setString(13, bean.getRole().name());
			} else {
				pst.setNull(13, java.sql.Types.VARCHAR);
			}
			pst.setString(14, bean.getType().name());
			pst.setString(15, bean.getGender().name());
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
	public UserBean findById(Integer id) throws AppException {
		Connection conn = dbConnectionManager.getConnection();
		UserBean bean = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(getUserQuery("user.id"));
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			bean = rs.next() == true ? formatResult(rs) : null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(ps);
			dbConnectionManager.returnConnection(conn);
		}

		return bean;
	}

	@Override
	public UserBean findByEmail(String email) throws AppException {
		Connection conn = dbConnectionManager.getConnection();
		UserBean bean = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(getUserQuery("user.email"));
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			bean = rs.next() == true ? formatResult(rs) : null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(ps);
			dbConnectionManager.returnConnection(conn);
		}
		return bean;
	}

	@Override
	public List<UserBean> findAll(AccountStatus status) throws AppException {
		Connection conn = dbConnectionManager.getConnection();
		PreparedStatement ps = null;
		List<UserBean> users = new ArrayList<>();
		try {
			String query = "";
			if (status != null) {
				query = getUserQuery("user.status");
			} else {
				query = getUserQuery(null);
			}
			ps = conn.prepareStatement(query);
			if (status != null) {
				ps.setString(1, status.name());
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserBean bean = formatResult(rs);
				users.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(ps);
			dbConnectionManager.returnConnection(conn);
		}

		return users;
	}

	@Override
	public Boolean update(UserBean bean) throws AppException {
		LOGGER.info("Update user :: " + bean);
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			boolean isLocationUpdated = locationRepository.update(bean.getLocation());
			String query = "update user set first_name = ?, last_name = ?, email = ?, password = ?, phone = ?, status = ?, created_at = ?, updated_at = ?,"
					+ " image_secure_url = ?, image_url = ?, image_public_id = ?, role = ? where id = ?";

			pst = conn.prepareStatement(query);
			pst.setString(1, bean.getFirstName());
			pst.setString(2, bean.getLastName());
			pst.setString(3, bean.getEmail());
			pst.setString(4, bean.getPassword());
			pst.setString(5, bean.getPhone());
			pst.setString(6, bean.getStatus().name());
			pst.setLong(7, bean.getCreatedAt());
			pst.setLong(8, bean.getUpdatedAt());
			pst.setString(9, bean.getImage().getImageUrl());
			pst.setString(10, bean.getImage().getSecureUrl());
			pst.setString(11, bean.getImage().getPublicId());
			if (bean.getRole() != null) {
				pst.setString(12, bean.getRole().name());
			} else {
				pst.setNull(12, java.sql.Types.VARCHAR);
			}
			pst.setInt(13, bean.getId());

			return pst.executeUpdate() > 0 || isLocationUpdated;

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
		UserBean bean = findById(id);
		if (bean != null) {
			bean.setStatus(AccountStatus.DISABLED);
			bean.setUpdatedAt(CommonUtil.getCurrentTimeInMillis());
			return update(bean);
		}
		return false;
	}

	private String getUserQuery(String clause) {
		String query = "select user.id, user.first_name, user.last_name, user.gender, user.email, user.password, user.phone, user.role, user.type, user.status, user.created_at, user.updated_at, "
				+ "user.image_secure_url, user.image_url, user.image_public_id , location.id, location.addressline, location.city, location.state, location.zipcode, location.country,"
				+ "location.latitude, location.longitude from user left outer join location on user.location_id = location.id CONDITION order by user.id";
		if (clause != null) {
			query = query.replace("CONDITION", "where " + clause + " = ?");
		} else {
			query = query.replace("CONDITION", "");
		}
		return query;
	}

	private UserBean formatResult(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		UserBean bean = null;
		LocationBean location = null;
		ImageBean image = null;
		bean = new UserBean();
		location = new LocationBean();
		image = new ImageBean();
		if (CommonDBUtil.findIfColumnExists("user.id", rs)) {
			bean.setId(rs.getInt("user.id"));
		}
		if (CommonDBUtil.findIfColumnExists("user.first_name", rs)) {
			bean.setFirstName(rs.getString("user.first_name"));
		}
		if (CommonDBUtil.findIfColumnExists("user.last_name", rs)) {
			bean.setLastName(rs.getString("user.last_name"));
		}
		if (CommonDBUtil.findIfColumnExists("user.email", rs)) {
			bean.setEmail(rs.getString("user.email"));
		}
		if (CommonDBUtil.findIfColumnExists("user.password", rs)) {
			bean.setPassword(rs.getString("user.password"));
		}
		if (CommonDBUtil.findIfColumnExists("user.phone", rs)) {
			bean.setPhone(rs.getString("user.phone"));
		}
		if (CommonDBUtil.findIfColumnExists("user.type", rs)) {
			bean.setType(UserType.valueOf(rs.getString("user.type")));
		}
		if (CommonDBUtil.findIfColumnExists("user.role", rs) && rs.getString("user.role") != null) {
			bean.setRole(UserRole.valueOf(rs.getString("user.role")));
		}
		if (CommonDBUtil.findIfColumnExists("user.status", rs)) {
			bean.setStatus(AccountStatus.valueOf(rs.getString("user.status")));
		}
		if (CommonDBUtil.findIfColumnExists("user.updated_at", rs)) {
			bean.setUpdatedAt(rs.getLong("user.updated_at"));
		}
		if (CommonDBUtil.findIfColumnExists("user.created_at", rs)) {
			bean.setCreatedAt(rs.getLong("user.created_at"));
		}
		if (CommonDBUtil.findIfColumnExists("user.gender", rs)) {
			bean.setGender(Gender.valueOf(rs.getString("user.gender")));
		}
		if (CommonDBUtil.findIfColumnExists("user.image_secure_url", rs)) {
			image.setSecureUrl(rs.getString("user.image_secure_url"));
		}
		if (CommonDBUtil.findIfColumnExists("user.image_url", rs)) {
			image.setImageUrl(rs.getString("user.image_url"));
		}
		if (CommonDBUtil.findIfColumnExists("user.image_public_id", rs)) {
			image.setPublicId(rs.getString("user.image_public_id"));
		}
		if (CommonDBUtil.findIfColumnExists("location.id", rs)) {
			location.setId(rs.getInt("location.id"));
		}
		if (CommonDBUtil.findIfColumnExists("location.addressline", rs)) {
			location.setAddressLine(rs.getString("location.addressline"));
		}
		if (CommonDBUtil.findIfColumnExists("location.city", rs)) {
			location.setCity(rs.getString("location.city"));
		}
		if (CommonDBUtil.findIfColumnExists("location.state", rs)) {
			location.setState(rs.getString("location.state"));
		}
		if (CommonDBUtil.findIfColumnExists("location.zipcode", rs)) {
			location.setZipcode(rs.getString("location.zipcode"));
		}
		if (CommonDBUtil.findIfColumnExists("location.country", rs)) {
			location.setCountry(rs.getString("location.country"));
		}
		if (CommonDBUtil.findIfColumnExists("location.latitude", rs)) {
			location.setLatitude(rs.getBigDecimal("location.latitude"));
		}
		if (CommonDBUtil.findIfColumnExists("location.longitude", rs)) {
			location.setLongitude(rs.getBigDecimal("location.longitude"));
		}

		bean.setLocation(location);
		bean.setImage(image);

		return bean;
	}

	public static IUserRepository getInstance() {
		synchronized (UserRepositoryImpl.class) {
			if (userRepository == null) {
				userRepository = new UserRepositoryImpl();
			}
		}
		return userRepository;
	}

	public static void main(String[] args) throws AppException {
		System.out.println(UserRepositoryImpl.getInstance().findByEmail("a@b.com"));
	}
}
