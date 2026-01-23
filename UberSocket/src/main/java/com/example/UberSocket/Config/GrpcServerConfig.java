package com.example.UberSocket.Config;

import com.example.Uber.RideNotificationServiceGrpc;
import com.example.UberSocket.Services.RideNotificationServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    @Value("${grpc.server.port:9091}")
    private int grpcServerPort;

    private  final RideNotificationServiceImpl rideNotificationServiceimpl;
    private Server server;





    @PostConstruct
    public void startGrpcServer() throws IOException {
        server = ServerBuilder
                .forPort(grpcServerPort)
                .addService(rideNotificationServiceimpl)
                .build()
                .start();

        System.out.println("gRPC Server started on port " + grpcServerPort);
        System.out.println(
                RideNotificationServiceGrpc.getServiceDescriptor().getName()
        );


        new Thread(() -> {
            try {
                if( server != null ) {
                    server.awaitTermination();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("gRPC Server interrupted");
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC Server...");
            if( server != null ) {
                server.shutdown();
            }
        }));

    }
}