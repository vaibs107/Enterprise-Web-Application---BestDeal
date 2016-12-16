package com.foodquest.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foodquest.models.LocationBean;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.repository.ILocationRepository;
import com.foodquest.utils.lang.AppException;

public class LocationRepositoryImpl implements ILocationRepository {
	private static final Logger LOGGER = Logger.getLogger(LocationRepositoryImpl.class.getName());
	private static ILocationRepository locationRepository;
	private DBConnectionManager dbConnectionManager;

	private LocationRepositoryImpl() {
		dbConnectionManager = DBConnectionManager.getInstance();
	}

	@Override
	public Integer create(LocationBean bean) throws AppException {
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			String query = "insert into location (addressline, city, state, zipcode, country, latitude, longitude) values (?,?,?,?,?,?,?)";
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, bean.getAddressLine());
			pst.setString(2, bean.getCity());
			pst.setString(3, bean.getState());
			pst.setString(4, bean.getZipcode());
			pst.setString(5, bean.getCountry());
			pst.setBigDecimal(6, bean.getLatitude());
			pst.setBigDecimal(7, bean.getLongitude());
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
	public LocationBean findById(Integer id) throws AppException {
		PreparedStatement pst = null;
		LocationBean bean = null;
		Connection conn = null;
		try {
			String query = "select * from location where id = ?";
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			bean = formatResult(rs);
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
	public Boolean update(LocationBean bean) throws AppException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			String query = "update location set addressline = ?, city= ?, state = ?, zipcode = ?, country = ?, latitude = ?, longitude = ? where id = ?;";
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, bean.getAddressLine());
			pst.setString(2, bean.getCity());
			pst.setString(3, bean.getState());
			pst.setString(4, bean.getZipcode());
			pst.setString(5, bean.getCountry());
			pst.setBigDecimal(6, bean.getLatitude());
			pst.setBigDecimal(7, bean.getLongitude());
			pst.setInt(8, bean.getId());
			return pst.executeUpdate() > 0;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			return false;
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
			String query = "delete from location where id = ?";
			conn = dbConnectionManager.getConnection();
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception ", e);
			return false;
		} finally {
			CommonDBUtil.close(pst);
			dbConnectionManager.returnConnection(conn);
		}
	}

	private LocationBean formatResult(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		LocationBean bean = null;
		if (rs.next()) {
			bean = new LocationBean();
			if (CommonDBUtil.findIfColumnExists("id", rs)) {
				bean.setId(rs.getInt("id"));
			}
			if (CommonDBUtil.findIfColumnExists("addressline", rs)) {
				bean.setAddressLine(rs.getString("addressline"));
			}
			if (CommonDBUtil.findIfColumnExists("city", rs)) {
				bean.setCity(rs.getString("city"));
			}
			if (CommonDBUtil.findIfColumnExists("state", rs)) {
				bean.setState(rs.getString("state"));
			}
			if (CommonDBUtil.findIfColumnExists("zipcode", rs)) {
				bean.setZipcode(rs.getString("zipcode"));
			}
			if (CommonDBUtil.findIfColumnExists("country", rs)) {
				bean.setCountry(rs.getString("country"));
			}
			if (CommonDBUtil.findIfColumnExists("latitude", rs)) {
				bean.setLatitude(rs.getBigDecimal("latitude"));
			}
			if (CommonDBUtil.findIfColumnExists("longitude", rs)) {
				bean.setLongitude(rs.getBigDecimal("longitude"));
			}
		}
		return bean;
	}

	public static ILocationRepository getInstance() {
		synchronized (LocationRepositoryImpl.class) {
			if (locationRepository == null) {
				locationRepository = new LocationRepositoryImpl();
			}
		}
		return locationRepository;
	}
}
