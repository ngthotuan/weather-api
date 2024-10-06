package com.skyapi.weatherforecast;

/**
 * Created by tuannt7 on 06/10/2024
 */

public class GeolocationException extends Exception {

	public GeolocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeolocationException(String message) {
		super(message);
	}

}
