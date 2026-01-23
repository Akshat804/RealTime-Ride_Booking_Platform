package com.example.UberSocket.Client;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.Uber.RideServiceGrpc;
import com.example.Uber.RideAcceptanceRequest;
import com.example.Uber.RideAcceptanceResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;

@Component
@Configuration
@Slf4j
public class GrpcClient {

    @Value("${grpc.client.port:9090}")
    private int grpcClientPort;

    @Value("${grpc.client.host:localhost}")
    private String grpcClientHost;

    private ManagedChannel channel;
    private RideServiceGrpc.RideServiceBlockingStub rideServiceStub;


    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(grpcClientHost, grpcClientPort)
                .usePlaintext()
                .build();

        rideServiceStub = RideServiceGrpc.newBlockingStub(channel);
    }

    public boolean acceptRide(long bookingId,Integer driverId) {
        RideAcceptanceRequest request = RideAcceptanceRequest.newBuilder().setDriverId(driverId).setBookingId((int) bookingId).build();
        try {
            RideAcceptanceResponse response = rideServiceStub.acceptRide(request);
            return response.getSuccess();
        } catch (StatusRuntimeException e) {
            log.warn("Accept ride failed with status {}", e.getStatus());
            return false;
        }
    }


}