package com.example.Uber.configuration;

import java.io.IOException;

import com.example.Uber.service.impl.RideServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    @Value("${grpc.server.port:9090}")
    private  int grpcServerPort;

    private final RideServiceImp rideServiceImp;
    private Server server;
    @PostConstruct
    public void startGrpcServer() throws IOException {
        server = ServerBuilder
                .forPort(grpcServerPort)
                .addService(rideServiceImp)
                .build()
                .start();

        System.out.println("gRPC Server started on port " + grpcServerPort);


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