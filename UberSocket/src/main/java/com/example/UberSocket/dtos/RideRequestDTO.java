package com.example.UberSocket.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@Builder
public class RideRequestDTO {
    private String pickUpLocationLatitude;
    private String pickUpLocationLongitude;
    private Integer bookingId;
    private List<Integer> driverIds;




}
