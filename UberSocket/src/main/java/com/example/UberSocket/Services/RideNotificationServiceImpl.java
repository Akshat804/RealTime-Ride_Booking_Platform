package com.example.UberSocket.Services;

import com.example.Uber.RideNotificationRequest;
import com.example.Uber.RideNotificationResponse;
import com.example.Uber.RideNotificationServiceGrpc;
import com.example.UberSocket.dtos.RideRequestDTO;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideNotificationServiceImpl extends RideNotificationServiceGrpc.RideNotificationServiceImplBase {
   private final SocketService socketService;

    @Override
    public void notifyDriversForNewRide(RideNotificationRequest request, StreamObserver<RideNotificationResponse>responseStreamObserver){

        System.out.println(">>> UberSocket gRPC RECEIVED REQUEST");
        System.out.println("Booking ID = " + request.getBookingId());
        System.out.println("Driver IDs = " + request.getDriverIdsList());

        RideRequestDTO rideRequestDTO=RideRequestDTO.builder()
               .pickUpLocationLatitude(request.getPickUpLocationLatitude()).
               pickUpLocationLongitude(request.getPickUpLocationLongitude()).
               bookingId(request.getBookingId()).
               driverIds(request.getDriverIdsList())
               .build();
        socketService.notifyDriversForNewRide(rideRequestDTO);
        responseStreamObserver.onNext(RideNotificationResponse.newBuilder().setSuccess(true).build());
        responseStreamObserver.onCompleted();
    }

}
