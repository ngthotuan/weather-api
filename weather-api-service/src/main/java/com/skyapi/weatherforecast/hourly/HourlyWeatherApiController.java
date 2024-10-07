package com.skyapi.weatherforecast.hourly;

import com.skyapi.weatherforecast.BadRequestException;
import com.skyapi.weatherforecast.CommonUtility;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuannt7 on 07/10/2024
 */

@RestController
@RequestMapping("/v1/hourly")
@Validated
@RequiredArgsConstructor
public class HourlyWeatherApiController {

	private final HourlyWeatherService hourlyWeatherService;
	private final GeolocationService locationService;
	private final ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<?> listHourlyForecastByIPAddress(HttpServletRequest request) {
		String ipAddress = CommonUtility.getIPAddress(request);

		try {
			int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));

			Location locationFromIP = locationService.getLocation(ipAddress);

			List<HourlyWeather> hourlyForecast = hourlyWeatherService.getByLocation(locationFromIP, currentHour);

			if (hourlyForecast.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(listEntity2DTO(hourlyForecast));

		} catch (NumberFormatException ex) {

			return ResponseEntity.badRequest().build();

		}
	}

	@GetMapping("/{locationCode}")
	public ResponseEntity<?> listHourlyForecastByLocationCode(
			@PathVariable("locationCode") String locationCode, HttpServletRequest request) {

		try {
			int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));

			List<HourlyWeather> hourlyForecast = hourlyWeatherService.getByLocationCode(locationCode, currentHour);

			if (hourlyForecast.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(listEntity2DTO(hourlyForecast));

		} catch (NumberFormatException ex) {

			return ResponseEntity.badRequest().build();

		}
	}


	@PutMapping("/{locationCode}")
	public ResponseEntity<?> updateHourlyForecast(@PathVariable("locationCode") String locationCode,
			@RequestBody @Valid List<HourlyWeatherDTO> listDTO) throws BadRequestException {

		if (listDTO.isEmpty()) {
			throw new BadRequestException("Hourly forecast data cannot be empty");
		}

		List<HourlyWeather> listHourlyWeather = listDTO2ListEntity(listDTO);

		List<HourlyWeather> updateHourlyWeather = hourlyWeatherService.updateByLocationCode(locationCode, listHourlyWeather);

		return ResponseEntity.ok(listEntity2DTO(updateHourlyWeather));

	}

	private List<HourlyWeather> listDTO2ListEntity(List<HourlyWeatherDTO> listDTO) {
		List<HourlyWeather> listEntity = new ArrayList<>();

		listDTO.forEach(dto -> listEntity.add(modelMapper.map(dto, HourlyWeather.class)));

		return listEntity;
	}

	private HourlyWeatherListDTO listEntity2DTO(List<HourlyWeather> hourlyForecast) {
		Location location = hourlyForecast.getFirst().getId().getLocation();

		HourlyWeatherListDTO listDTO = new HourlyWeatherListDTO();
		listDTO.setLocation(location.toString());

		hourlyForecast.forEach(hourlyWeather -> {
			HourlyWeatherDTO dto = modelMapper.map(hourlyWeather, HourlyWeatherDTO.class);
			listDTO.addWeatherHourlyDTO(dto);
		});

		return listDTO;

	}

}
