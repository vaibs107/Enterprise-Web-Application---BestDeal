package com.foodquest.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.foodquest.models.LocationBean;
import com.foodquest.models.db.MongoDBConnectionManager;
import com.foodquest.repository.IGeoLocationRepository;
import com.foodquest.utils.lang.AppException;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class GeoLocationRepositoryImpl implements IGeoLocationRepository {
	private static IGeoLocationRepository INSTANCE = null;
	private static String COLLECTION = "distributor_locations";
	private MongoDBConnectionManager dbManager;
	private DB appDB;

	private GeoLocationRepositoryImpl() {
		dbManager = MongoDBConnectionManager.INSTANCE;
		appDB = dbManager.getAppDB();
	}

	@Override
	public String create(LocationBean location) throws AppException {
		ObjectId id = new ObjectId();
		try {
			BasicDBObject loc = getLocationObject(location);

			BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start().add("location", loc)
					.add("addressLine", location.getAddressLine()).add("city", location.getCity())
					.add("state", location.getState()).add("country", location.getCountry())
					.add("zipcode", location.getZipcode()).add("id", location.getId()).add("_id", location.getId());
			appDB.getCollection(COLLECTION).insert(docBuilder.get());
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}

		return id.toString();
	}

	@Override
	public LocationBean findById(Integer id) throws AppException {
		LocationBean location = null;
		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCursor cursor = appDB.getCollection(COLLECTION).find(query);
			while (cursor.hasNext()) {
				location = formatDBOject(cursor.next());
			}
			return location;
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public Boolean update(LocationBean location) throws AppException {
		try {
			BasicDBObject loc = getLocationObject(location);
			BasicDBObject doc = new BasicDBObject("location", loc);
			BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start().add("location", loc)
					.add("addressLine", location.getAddressLine()).add("city", location.getCity())
					.add("state", location.getState()).add("country", location.getCountry())
					.add("zipcode", location.getZipcode()).add("id", location.getId());
			BasicDBObject query = new BasicDBObject("id", location.getId());
			System.out.println(findById(location.getId()));
			appDB.getCollection(COLLECTION).update(query, docBuilder.get());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) throws AppException {
		try {
			BasicDBObject query = new BasicDBObject("id", id);
			appDB.getCollection(COLLECTION).remove(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<LocationBean> search(LocationBean location, Double radius) throws AppException {
		List<LocationBean> locations = new ArrayList<LocationBean>();
		try {
			BasicDBObject geo = new BasicDBObject();
			BasicDBList list = new BasicDBList();
			list.add(getLatLongListObject(location));
			list.add((radius / 3963.2));
			geo.append("$geoWithin", new BasicDBObject("$centerSphere", list));
			BasicDBObject query = new BasicDBObject("location", geo);
			System.out.println(query);
			DBCursor cursor = appDB.getCollection(COLLECTION).find(query);
			while (cursor.hasNext()) {
				locations.add(formatDBOject(cursor.next()));
			}
			return locations;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
	}

	private BasicDBObject getLocationObject(LocationBean location) {
		BasicDBObject loc = new BasicDBObject();
		BasicDBList list = getLatLongListObject(location);
		loc.append("type", "Point");
		loc.append("coordinates", list);
		return loc;
	}

	private BasicDBList getLatLongListObject(LocationBean location) {
		BasicDBList list = new BasicDBList();
		list.add(location.getLongitude().doubleValue());
		list.add(location.getLatitude().doubleValue());
		return list;
	}

	private LocationBean formatDBOject(DBObject dbo) {
		if (dbo == null) {
			return null;
		}
		LocationBean bean = new LocationBean();
		if (dbo.containsField("location")) {
			DBObject loc = (DBObject) dbo.get("location");
			List<Double> coordinates = (List<Double>) loc.get("coordinates");
			bean.setLatitude(new BigDecimal(coordinates.get(0)));
			bean.setLongitude(new BigDecimal(coordinates.get(1)));
		}
		if (dbo.containsField("_id")) {
			bean.set_id(dbo.get("_id").toString());
		}
		if (dbo.containsField("id")) {
			bean.setId(Integer.parseInt(dbo.get("id").toString()));
		}
		if (dbo.containsField("addressLine")) {
			bean.setAddressLine(dbo.get("addressLine").toString());
		}
		if (dbo.containsField("city")) {
			bean.setCity(dbo.get("city").toString());
		}
		if (dbo.containsField("state")) {
			bean.setState(dbo.get("state").toString());
		}
//		if (dbo.containsField("country")) {
//			bean.setCountry(dbo.get("country").toString());
//		}
		if (dbo.containsField("zipcode")) {
			bean.setZipcode(dbo.get("zipcode").toString());
		}
		return bean;
	}

	public static IGeoLocationRepository getInstance() {
		synchronized (GeoLocationRepositoryImpl.class) {
			if (INSTANCE == null) {
				INSTANCE = new GeoLocationRepositoryImpl();
			}
		}
		return INSTANCE;
	}

	public static void main(String[] args) throws AppException {
		GeoLocationRepositoryImpl g = new GeoLocationRepositoryImpl();
		LocationBean location = new LocationBean();
		location.setLatitude(new BigDecimal(-87.618247));
		location.setLongitude(new BigDecimal(41.839549));
		g.search(location, 2d);
	}
}
