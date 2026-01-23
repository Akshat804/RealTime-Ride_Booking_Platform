package com.example.UberSocket.Services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.UberSocket.dtos.DriverNotificationdto;
import com.example.UberSocket.dtos.RideRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyDriversForNewRide(RideRequestDTO rideRequestDTO) {
        DriverNotificationdto driverNotificationDTO = DriverNotificationdto.builder()
                .pickUpLocationLatitude(rideRequestDTO.getPickUpLocationLatitude())
                .pickUpLocationLongitude(rideRequestDTO.getPickUpLocationLongitude())
                .bookingId(rideRequestDTO.getBookingId())
                .build();
        for (Integer driverId : rideRequestDTO.getDriverIds()) {
            log.info("Sending new ride to driver {}", driverId);
            messagingTemplate.convertAndSend(
                    "/topic/new-ride/" + driverId,
                    driverNotificationDTO
            );
            System.out.println("New ride sent to driver " + driverId);
        }

    }


}