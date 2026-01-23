package com.example.UberSocket.Controller;

import com.example.UberSocket.Client.GrpcClient;
import com.example.UberSocket.dtos.RideAcceptanceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SocketController {

    private final GrpcClient grpcClient;

    @MessageMapping("/ride-acceptance")
    public void receiveRideAcceptance(RideAcceptanceDTO dto) {

        try {
            boolean success = grpcClient.acceptRide(
                    dto.getBookingId(),
                    dto.getDriverId()
            );
            log.info(
                    "ACCEPT CLICKED -> driverId={}, bookingId={}",
                    dto .getDriverId(),
                    dto.getBookingId()
            );


            if (success) {
                log.info(
                        "Ride accepted successfully: driverId={}, bookingId={}",
                        dto.getDriverId(),
                        dto.getBookingId()
                );
            } else {
                log.info(
                        "Ride already accepted by another driver: driverId={}, bookingId={}",
                        dto.getDriverId(),
                        dto.getBookingId()
                );
            }

        } catch (Exception e) {
            log.error("Driver REQUEST FAILED due to exception", e);
        }
    }
}
