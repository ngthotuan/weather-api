package com.skyapi.weatherforecast.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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

	@OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@JsonIgnore
	private RealtimeWeather realtimeWeather;

	public Location(String cityName, String regionName, String countryName, String countryCode) {
		this.cityName = cityName;
		this.regionName = regionName;
		this.countryName = countryName;
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return cityName + ", " + (regionName != null ? regionName + ", " : "") + countryName;
	}

}
