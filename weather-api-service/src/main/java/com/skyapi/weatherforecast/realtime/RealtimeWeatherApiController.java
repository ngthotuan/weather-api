package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.CommonUtility;
import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealtimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Slf4j
@RestController
@RequestMapping("/v1/realtime")
@RequiredArgsConstructor
public class RealtimeWeatherApiController {

	private final GeolocationService locationService;
	private final RealtimeWeatherService realtimeWeatherService;
	private final ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<?> getRealtimeWeatherByIPAddress(HttpServletRequest request) {
		String ipAddress = CommonUtility.getIPAddress(request);

		try {
			Location locationFromIP = locationService.getLocation(ipAddress);
			RealtimeWeather realtimeWeather = realtimeWeatherService.getByLocation(locationFromIP);

			RealtimeWeatherDTO dto = modelMapper.map(realtimeWeather, RealtimeWeatherDTO.class);

			return ResponseEntity.ok(dto);

		} catch (GeolocationException e) {
			log.error(e.getMessage(), e);

			return ResponseEntity.badRequest().build();

		} catch (LocationNotFoundException e) {
			log.error(e.getMessage(), e);

			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{locationCode}")
	public ResponseEntity<?> getRealtimeWeatherByLocationCode(@PathVariable("locationCode") String locationCode) {
		try {
			RealtimeWeather realtimeWeather = realtimeWeatherService.getByLocationCode(locationCode);

			return ResponseEntity.ok(entity2DTO(realtimeWeather));

		} catch (LocationNotFoundException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{locationCode}")
	public ResponseEntity<?> updateRealtimeWeather(@PathVariable("locationCode") String locationCode,
			@RequestBody @Valid RealtimeWeather realtimeWeatherInRequest) {

		realtimeWeatherInRequest.setLocationCode(locationCode);

		try {
			RealtimeWeather updatedRealtimeWeather = realtimeWeatherService.update(locationCode, realtimeWeatherInRequest);

			return ResponseEntity.ok(entity2DTO(updatedRealtimeWeather));

		} catch (LocationNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private RealtimeWeatherDTO entity2DTO(RealtimeWeather realtimeWeather) {
		return modelMapper.map(realtimeWeather, RealtimeWeatherDTO.class);
	}

}
