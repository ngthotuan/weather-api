package com.skyapi.weatherforecast;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Slf4j
public class CommonUtility {

	public static String getIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.isEmpty()) {
			ip = request.getRemoteAddr();
		}

		log.info("Client's IP Address: {}", ip);

		return ip;
	}
}
