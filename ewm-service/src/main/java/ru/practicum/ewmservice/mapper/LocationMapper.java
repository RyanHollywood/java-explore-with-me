package ru.practicum.ewmservice.mapper;

import ru.practicum.ewmservice.dto.location.LocationDto;
import ru.practicum.ewmservice.model.Location;

public class LocationMapper {

    public static Location fromLocationDto(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}