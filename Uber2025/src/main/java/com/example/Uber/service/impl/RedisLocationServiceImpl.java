package com.example.Uber.service.impl;

import com.example.Uber.dto.DriverLocationDTO;
import com.example.Uber.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RedisLocationServiceImpl implements LocationService {

    private static final String DRIVER_GEO_OPS_KEY="driver:geo";
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude){
        GeoOperations<String,String> geoOperations= stringRedisTemplate.opsForGeo();
        geoOperations.add(DRIVER_GEO_OPS_KEY,new RedisGeoCommands.GeoLocation<String>(driverId,new Point(latitude,longitude)));
        return true;





    }



    public List<DriverLocationDTO> getNearbyDrivers(
            Double latitude,
            Double longitude,
            Double radius
    ) {
        GeoOperations<String, String> geoOperations = stringRedisTemplate.opsForGeo();

        Distance circleRadius = new Distance(radius, Metrics.KILOMETERS);

        // 🔴 FIX IS HERE 👇 (longitude FIRST)
        Circle circle = new Circle(
                new Point(longitude, latitude),
                circleRadius
        );

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                geoOperations.radius(DRIVER_GEO_OPS_KEY, circle);

        List<DriverLocationDTO> driverLocations = new ArrayList<>();

        if (results == null) return driverLocations;

        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {

            Point point = geoOperations
                    .position(DRIVER_GEO_OPS_KEY, result.getContent().getName())
                    .get(0);

            DriverLocationDTO driverLocation = DriverLocationDTO.builder()
                    .driverId(Integer.valueOf(result.getContent().getName()))
                    .latitude(point.getY())   // Y = latitude ✅
                    .longitude(point.getX())  // X = longitude ✅
                    .build();

            driverLocations.add(driverLocation);
        }

        return driverLocations;
    }

}
