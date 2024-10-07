package com.skyapi.weatherforecast.location;

/**
 * Created by tuannt7 on 06/10/2024
 */

public class LocationNotFoundException extends RuntimeException {

	public LocationNotFoundException(String locationCode) {
		super("No location found with the given code: " + locationCode);
	}


	public LocationNotFoundException(String countryCode, String cityName) {
		super("No location found with the given country code: " + countryCode + " and city name: " + cityName);
	}

}
