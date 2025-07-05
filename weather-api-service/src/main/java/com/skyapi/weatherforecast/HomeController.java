package com.skyapi.weatherforecast;

import com.skyapi.weatherforecast.common.Location;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tuannt7 on 05/07/2025
 */

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public Object clientInfo(HttpServletRequest request,
                             GeolocationService geolocationService,
                             @RequestParam (required = false) String ip) {

        if (StringUtils.isNotBlank(ip)) {
            ip = ip.trim();
        } else {
            ip = CommonUtility.getIPAddress(request);
        }
        Location locationFromIP = geolocationService.getLocation(ip);

        return Map.of(
                "ipAddress", ip,
                "location", Map.of(
                        "cityName", locationFromIP.getCityName(),
                        "regionName", locationFromIP.getRegionName(),
                        "countryCode", locationFromIP.getCountryCode(),
                        "countryName", locationFromIP.getCountryName()
                )
        );
    }

    @GetMapping("/props")
    public Properties props() {
        return System.getProperties();
    }

    @GetMapping("/env")
    public Map<String,String> env() {
        return System.getenv();
    }

    @GetMapping("/runtime")
    public static Map<String, Object> runtime() {
        Runtime runtime = Runtime.getRuntime();

        Map<String, Object> info = new HashMap<>();
        info.put("availableProcessors", runtime.availableProcessors());
        info.put("maxMemoryBytes", runtime.maxMemory());
        info.put("totalMemoryBytes", runtime.totalMemory());
        info.put("freeMemoryBytes", runtime.freeMemory());
        // Thêm thông tin theo đơn vị MB để tiện quan sát
        info.put("maxMemoryMB", runtime.maxMemory() / (1024 * 1024));
        info.put("totalMemoryMB", runtime.totalMemory() / (1024 * 1024));
        info.put("freeMemoryMB", runtime.freeMemory() / (1024 * 1024));

        return info;
    }
}
