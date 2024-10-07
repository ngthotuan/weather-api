package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository repo;

	public Location add(Location location) {
		return repo.save(location);
	}

	public List<Location> list() {
		return repo.findUntrashed();
	}

	public Location get(String code) {
		return repo.findByCode(code);
	}

	public Location update(Location locationInRequest) throws LocationNotFoundException {
		String code = locationInRequest.getCode();

		Location locationInDB = repo.findByCode(code);

		if (locationInDB == null) {
			throw new LocationNotFoundException(code);
		}

		locationInDB.setCityName(locationInRequest.getCityName());
		locationInDB.setRegionName(locationInRequest.getRegionName());
		locationInDB.setCountryCode(locationInRequest.getCountryCode());
		locationInDB.setCountryName(locationInRequest.getCountryName());
		locationInDB.setEnabled(locationInRequest.isEnabled());

		return repo.save(locationInDB);
	}

	public void delete(String code) throws LocationNotFoundException {
		Location location = repo.findByCode(code);

		if (location == null) {
			throw new LocationNotFoundException(code);
		}

		repo.trashByCode(code);
	}
}
