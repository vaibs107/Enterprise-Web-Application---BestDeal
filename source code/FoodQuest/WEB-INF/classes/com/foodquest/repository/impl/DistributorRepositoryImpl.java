package com.foodquest.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foodquest.models.DistributorBean;
import com.foodquest.models.DistributorBean.DistributorStatus;
import com.foodquest.models.ImageBean;
import com.foodquest.models.LocationBean;
import com.foodquest.models.Pair;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.repository.IDistributorRepository;
import com.foodquest.repository.IGeoLocationRepository;
import com.foodquest.repository.ILocationRepository;
import com.foodquest.utils.lang.AppException;

public class DistributorRepositoryImpl implements IDistributorRepository {
	private static final Logger LOGGER = Logger.getLogger(DistributorRepositoryImpl.class.getName());
	private static IDistributorRepository distributorRepository = null;
	private DBConnectionManager dbConnectionManager;
	private ILocationRepository locationRepository;
	private IGeoLocationRepository geoLocationRepository;

	private DistributorRepositoryImpl() {
		dbConnectionManager = DBConnectionManager.getInstance();
		locationRepository = LocationRepositoryImpl.getInstance();
		geoLocationRepository = GeoLocationRepositoryImpl.getInstance();
	}

	@Override
	public Integer create(DistributorBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			System.out.println(bean);
			conn = dbConnectionManager.getConnection();
			if (bean.getLocation().getId() == null) {
				bean.getLocation().setId(locationRepository.create(bean.getLocation()));
				geoLocationRepository.create(bean.getLocation());
			}
			String query = "insert into distributor (name, website_url, phone, location_id, image_secure_url, image_url, image_public_id, status) values (?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getWebsiteUrl());
			pst.setString(3, bean.getPhone());
			pst.setInt(4, bean.getLocation().getId());
			pst.setString(5, bean.getImage().getSecureUrl());
			pst.setString(6, bean.getImage().getImageUrl());
			pst.setString(7, bean.getImage().getPublicId());
			pst.setString(8, bean.getStatus().name());
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
	public DistributorBean findById(Integer id) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		DistributorBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(getSelectQuery("distributor.id"));
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
	public DistributorBean findByName(String name) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		DistributorBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(getSelectQuery("distributor.name"));
			pst.setString(1, name);
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
	public DistributorBean findByLocation(LocationBean location) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		DistributorBean bean = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(getSelectQuery("distributor.location_id"));
			pst.setInt(1, location.getId());
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
	public List<DistributorBean> findAll(DistributorStatus status, String name) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		List<DistributorBean> distributors = new ArrayList<DistributorBean>();
		try {
			conn = dbConnectionManager.getConnection();
			HashMap<String, Pair<Object, Class>> paramsToType = new LinkedHashMap<>();
			if (status != null) {
				paramsToType.put("distributor.status", new Pair<Object, Class>(status.name(), String.class));
			}
			if (name != null) {
				paramsToType.put("distributor.name", new Pair<Object, Class>(name, String.class));
			}
			String query = getSelectQuery(paramsToType.keySet());
			pst = conn.prepareStatement(query);
			int i = 1;
			for (Entry<String, Pair<Object, Class>> entry : paramsToType.entrySet()) {
				pst = setParam(i, entry.getValue().getLeft(), entry.getValue().getRight(), pst);
				i++;
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				distributors.add(formatResultSet(rs));
			}
			return distributors;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	@Override
	public Boolean update(DistributorBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			boolean isLocationUpdated = locationRepository.update(bean.getLocation());
			if (isLocationUpdated) {
				geoLocationRepository.update(bean.getLocation());
			}
			String query = "update distributor set name = ?, website_url = ?, phone = ?, image_secure_url = ?, image_url = ?, image_public_id = ?, total_orders_delivered = ?, total_orders_to_be_delivered = ?, status = ? where id = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getWebsiteUrl());
			pst.setString(3, bean.getPhone());
			pst.setString(4, bean.getImage().getSecureUrl());
			pst.setString(5, bean.getImage().getImageUrl());
			pst.setString(6, bean.getImage().getPublicId());
			pst.setInt(7, bean.getTotalOrdersDelivered());
			pst.setInt(8, bean.getTotalOrdersToBeDelivered());
			pst.setString(9, bean.getStatus().name());
			pst.setInt(10, bean.getId());
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
		DistributorBean bean = findById(id);
		if (bean != null) {
			bean.setStatus(DistributorStatus.DISABLED);
			return update(bean);
		}
		return false;
	}

	@Override
	public Boolean createUserDistributorMapping(Integer distributorId, Integer userId) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement("insert into user_ditributor (user_id, distributor_id) values (?,?)");
			pst.setInt(1, userId);
			pst.setInt(2, distributorId);
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
	public List<Integer> getUserIds(Integer distributorId) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		List<Integer> userIds = new ArrayList<>();
		try {
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement("select user_id from user_ditributor where distributor_id = ?");
			pst.setInt(1, distributorId);

			ResultSet rs = pst.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					userIds.add(rs.getInt("user_id"));
				}
			}
			return userIds;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			throw new AppException(e.getMessage());
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
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

	private String getSelectQuery(String clause) {
		String query = "select distributor.id, distributor.name, distributor.website_url, distributor.phone, distributor.total_orders_delivered, distributor.total_orders_to_be_delivered"
				+ ",distributor.image_secure_url, distributor.image_url, distributor.image_public_id, distributor.total_orders_to_be_delivered, distributor.status, location.id, location.addressline, location.city, location.state, location.zipcode, location.country,"
				+ "location.latitude, location.longitude from distributor left outer join location on distributor.location_id = location.id CONDITION order by distributor.id";
		if (clause != null) {
			query = query.replace("CONDITION", "where " + clause + " = ?");
		} else {
			query = query.replace("CONDITION", "");
		}
		return query;
	}

	private String getSelectQuery(Set<String> clauses) {
		String query = "select distributor.id, distributor.name, distributor.website_url, distributor.phone, distributor.total_orders_delivered, distributor.total_orders_to_be_delivered,"
				+ "distributor.image_secure_url, distributor.image_url, distributor.image_public_id, distributor.total_orders_to_be_delivered, distributor.status, location.id, location.addressline, location.city, location.state, location.zipcode, location.country,"
				+ "location.latitude, location.longitude from distributor left outer join location on distributor.location_id = location.id CONDITION order by distributor.id";
		StringBuilder builder = new StringBuilder();
		boolean isFirst = false;
		if (clauses.size() > 0) {
			for (String clause : clauses) {
				if (!isFirst) {
					builder.append("where " + clause + " = ?");
					isFirst = true;
				} else {
					builder.append("and " + clause + " = ?");
				}
			}
		} else {
			builder.append("");
		}
		query = query.replace("CONDITION", builder.toString());
		return query;
	}

	private DistributorBean formatResultSet(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		DistributorBean bean = new DistributorBean();
		LocationBean location = new LocationBean();
		ImageBean image = new ImageBean();
		if (CommonDBUtil.findIfColumnExists("distributor.id", rs)) {
			bean.setId(rs.getInt("distributor.id"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.name", rs)) {
			bean.setName(rs.getString("distributor.name"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.website_url", rs)) {
			bean.setWebsiteUrl(rs.getString("distributor.website_url"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.phone", rs)) {
			bean.setPhone(rs.getString("distributor.phone"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.total_orders_delivered", rs)) {
			bean.setTotalOrdersDelivered(rs.getInt("distributor.total_orders_delivered"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.total_orders_to_be_delivered", rs)) {
			bean.setTotalOrdersToBeDelivered(rs.getInt("distributor.total_orders_to_be_delivered"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.status", rs)) {
			bean.setStatus(DistributorStatus.valueOf(rs.getString("distributor.status")));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.image_secure_url", rs)) {
			image.setSecureUrl(rs.getString("distributor.image_secure_url"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.image_url", rs)) {
			image.setImageUrl(rs.getString("distributor.image_url"));
		}
		if (CommonDBUtil.findIfColumnExists("distributor.image_public_id", rs)) {
			image.setPublicId(rs.getString("distributor.image_public_id"));
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
		bean.setImage(image);
		bean.setLocation(location);
		return bean;
	}

	public static IDistributorRepository getInstance() {
		synchronized (DistributorRepositoryImpl.class) {
			if (distributorRepository == null) {
				distributorRepository = new DistributorRepositoryImpl();
			}
		}
		return distributorRepository;
	}

}
