package com.skyapi.weatherforecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tuannt7 on 06/10/2024
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
	private Date timestamp;
	private int status;
	private String path;
	private List<String> errors = new ArrayList<>();

	public void addError(String message) {
		this.errors.add(message);
	}

}
