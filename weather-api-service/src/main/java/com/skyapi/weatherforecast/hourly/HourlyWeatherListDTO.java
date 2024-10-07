package com.skyapi.weatherforecast.hourly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuannt7 on 07/10/2024
 */
@Data
public class HourlyWeatherListDTO {
	private String location;

	@JsonProperty("hourly_forecast")
	private List<HourlyWeatherDTO> hourlyForecast = new ArrayList<>();

	public void addWeatherHourlyDTO(HourlyWeatherDTO dto) {
		this.hourlyForecast.add(dto);
	}
}
