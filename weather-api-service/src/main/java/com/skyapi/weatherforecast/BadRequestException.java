package com.skyapi.weatherforecast;

/**
 * Created by tuannt7 on 06/10/2024
 */

public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}

}
