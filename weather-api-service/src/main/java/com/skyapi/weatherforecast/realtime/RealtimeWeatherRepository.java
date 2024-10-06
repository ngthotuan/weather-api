package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealtimeWeather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Repository
public interface RealtimeWeatherRepository extends CrudRepository<RealtimeWeather, String>{

	@Query("SELECT r FROM RealtimeWeather r WHERE r.location.countryCode = ?1 AND r.location.cityName = ?2")
    RealtimeWeather findByCountryCodeAndCity(String countryCode, String city);

	@Query("SELECT r FROM RealtimeWeather r WHERE r.id = ?1 AND r.location.trashed = false")
    RealtimeWeather findByLocationCode(String locationCode);
}
