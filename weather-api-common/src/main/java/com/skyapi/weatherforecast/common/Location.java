package com.skyapi.weatherforecast.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "locations")
public class Location {

	@Column(length = 12, nullable = false, unique = true)
	@Id
	@NotBlank
	private String code;

	@Column(length = 128, nullable = false)
	@JsonProperty("city_name")
	@NotBlank
	private String cityName;

	@Column(length = 128)
	@JsonProperty("region_name")
	@NotNull
	private String regionName;

	@Column(length = 64, nullable = false)
	@JsonProperty("country_name")
	@NotBlank
	private String countryName;

	@Column(length = 2, nullable = false)
	@JsonProperty("country_code")
	@NotBlank
	private String countryCode;

	private boolean enabled;

	@JsonIgnore
	private boolean trashed;
}
