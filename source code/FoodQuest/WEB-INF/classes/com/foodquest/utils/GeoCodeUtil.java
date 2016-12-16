package com.foodquest.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foodquest.models.LocationBean;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderStatus;

/**
 * GeoCodeUtil helps to calculate Latitude and Longitude based on address or
 * zipcode
 *
 * @author Shreyas K
 */

public class GeoCodeUtil {
	private static final Logger LOGGER = Logger.getLogger(GeoCodeUtil.class.getName());
	private final static Geocoder geocoder = new Geocoder();

	public static LocationBean doCalculateLatLon(LocationBean location) {
		StringBuilder builder = new StringBuilder();
		String searchAdddress = null;
		if (location.getAddressLine() != null && location.getCity() != null && location.getState() != null
				&& location.getCountry() != null && location.getZipcode() != null) {
			builder.append(location.getAddressLine() + ",");
			builder.append(location.getCity() + ",");
			builder.append(location.getState() + ",");
			builder.append(location.getCountry() + ",");
			builder.append(location.getZipcode());
			searchAdddress = builder.toString();
		} else {
			searchAdddress = location.getZipcode();
		}
		GeocodeResponse geocoderResponse = null;
		GeocoderStatus geoStatus = GeocoderStatus.UNKNOWN_ERROR;
		;
		geocoderResponse = getGeoCodeResponse(searchAdddress);
		geoStatus = geocoderResponse.getStatus();
		
		// if status response is null or status is error try again for zip code
		if (geocoderResponse == null || GeocoderStatus.ERROR == geoStatus || GeocoderStatus.INVALID_REQUEST == geoStatus
				|| GeocoderStatus.ZERO_RESULTS == geoStatus) {
			geocoderResponse = getGeoCodeResponse(location.getZipcode());
			geoStatus = geocoderResponse.getStatus();
		}

		switch (geoStatus) {
		case ERROR:
		case INVALID_REQUEST:
		case ZERO_RESULTS:
			LOGGER.log(Level.SEVERE, String.format("Invalid Address %s ", location.toString()));
			location.setLatitude(null);
			location.setLongitude(null);
		case OK:
			if (geocoderResponse.getResults().size() > 0) {
				BigDecimal lat = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat();
				BigDecimal lon = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng();

				LOGGER.log(Level.INFO, "lat " + lat + " long " + lon);
				location.setLatitude(lat);
				location.setLongitude(lon);
			} else {
				LOGGER.info(String.format("GEO code response OK but no results found. Response: %s ",
						geocoderResponse.toString()));
			}
		default:
			LOGGER.log(Level.SEVERE, "Geo code response not found");
		}

		return location;
	}

	private static GeocodeResponse getGeoCodeResponse(String searchAdddress) {
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(searchAdddress).setLanguage("en")
				.getGeocoderRequest();
		GeocodeResponse geocoderResponse = null;
		try {
			geocoderResponse = geocoder.geocode(geocoderRequest);

		} catch (IOException e) {
		}
		return geocoderResponse;
	}

}
