package com.example.Uber.Client;

import com.example.Uber.RideNotificationRequest;
import com.example.Uber.RideNotificationResponse;
import com.example.Uber.RideNotificationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
@Service
@Configuration
@Component
public class GrpcClient {
    @Value("${grpc.client.port:9091}")
    private int grpcClientPort;

    @Value("${grpc.client.host:localhost}")
    private String grpcClientHost;

    private ManagedChannel channel;
    private RideNotificationServiceGrpc.RideNotificationServiceBlockingStub stub;
@PostConstruct
    private synchronized void initIfRequired() {
        if (stub != null) return;
        System.out.println("CLIENT SERVICE DESCRIPTOR = " +
                RideNotificationServiceGrpc.getServiceDescriptor().getName()
        );

        System.out.println(">>> Initializing gRPC client");

        channel = ManagedChannelBuilder
                .forAddress(grpcClientHost, grpcClientPort)
                .usePlaintext()
                .build();

        stub = RideNotificationServiceGrpc.newBlockingStub(channel);

        System.out.println(">>> gRPC client initialized");
    }
    @PostConstruct
    public void forceInitForDebug() {
        System.out.println("FORCING GRPC CLIENT INIT FOR DEBUG");
        initIfRequired();
    }



    public boolean notifyDriversForNewRide(
            String pickUpLocationLatitude,
            String pickUpLocationLongitude,
            Integer bookingId,
            List<Integer> driverIds
    ) {

        initIfRequired(); // 🔥 GUARANTEED

        System.out.println(">>> Calling gRPC notifyDriversForNewRide");

        RideNotificationRequest request = RideNotificationRequest.newBuilder()
                .setPickUpLocationLatitude(pickUpLocationLatitude)
                .setPickUpLocationLongitude(pickUpLocationLongitude)
                .setBookingId(bookingId)
                .addAllDriverIds(driverIds)
                .build();

        try {
            RideNotificationResponse response =
                    stub.notifyDriversForNewRide(request);

            return response.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
