package com.skyapi.weatherforecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by tuannt7 on 06/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private LocalDateTime timestamp;
    private int code;
    private String error;
}
