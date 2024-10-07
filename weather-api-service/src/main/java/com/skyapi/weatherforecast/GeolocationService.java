package com.skyapi.weatherforecast;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyapi.weatherforecast.common.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Slf4j
@Service
public class GeolocationService {
	private final static String DBPath = "/ip2locdb/IP2LOCATION-LITE-DB3.BIN";
	private final static IP2Location ipLocator = new IP2Location();

	static {
		try (InputStream inputStream = GeolocationService.class.getResourceAsStream(DBPath)){
			if (inputStream == null) {
				throw new IOException("IP2Location database not found");
			}
            byte[] ip2locBytes = inputStream.readAllBytes();
			ipLocator.Open(ip2locBytes);
		} catch (Exception ex) {
			log.error("read IP2Location db exception: {}", ex.getMessage(), ex);
		}
	}

	public Location getLocation(String ipAddress) throws GeolocationException {

		try {
			IPResult result = ipLocator.IPQuery(ipAddress);

			if (!"OK".equals(result.getStatus())) {
				throw new GeolocationException("Geolocation failed with status: " + result.getStatus());
			}

			log.debug("ipAddress: {}, result: {}", ipAddress, result);

			return new Location(result.getCity(), result.getRegion(), result.getCountryLong(), result.getCountryShort());

		} catch (IOException ex) {
			throw new GeolocationException("Error querying IP database", ex);
		}

	}

}
