package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealtimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import com.skyapi.weatherforecast.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Service
@RequiredArgsConstructor
public class RealtimeWeatherService {

	private final RealtimeWeatherRepository realtimeWeatherRepo;
	private final LocationRepository locationRepo;

	public RealtimeWeather getByLocation(Location location) throws LocationNotFoundException {
		String countryCode = location.getCountryCode();
		String cityName = location.getCityName();

		RealtimeWeather realtimeWeather = realtimeWeatherRepo.findByCountryCodeAndCity(countryCode, cityName);

		if (realtimeWeather == null) {
			throw new LocationNotFoundException(countryCode, cityName);
		}

		return realtimeWeather;
	}

	public RealtimeWeather getByLocationCode(String locationCode) throws LocationNotFoundException {
		RealtimeWeather realtimeWeather = realtimeWeatherRepo.findByLocationCode(locationCode);

		if (realtimeWeather == null) {
			throw new LocationNotFoundException(locationCode);
		}

		return realtimeWeather;
	}

	public RealtimeWeather update(String locationCode, RealtimeWeather realtimeWeather) throws LocationNotFoundException {
		Location location = locationRepo.findByCode(locationCode);

		if (location == null) {
			throw new LocationNotFoundException(locationCode);
		}

		realtimeWeather.setLocation(location);
		realtimeWeather.setLastUpdated(new Date());

		if (location.getRealtimeWeather() == null) {
			location.setRealtimeWeather(realtimeWeather);
			Location updatedLocation = locationRepo.save(location);

			return updatedLocation.getRealtimeWeather();
		}

		return realtimeWeatherRepo.save(realtimeWeather);
	}
}
