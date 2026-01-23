package com.example.Uber.service.impl;

import org.springframework.stereotype.Service;

import com.example.Uber.RideAcceptanceRequest;
import com.example.Uber.RideAcceptanceResponse;
import com.example.Uber.RideServiceGrpc;
import com.example.Uber.service.BookingService;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;


    @Service
    @RequiredArgsConstructor
    public class RideServiceImp extends RideServiceGrpc.RideServiceImplBase {

        private final BookingService bookingService;

        @Override
        public void acceptRide(
                RideAcceptanceRequest request,
                StreamObserver<RideAcceptanceResponse> responseObserver) {

            try {
                boolean success = bookingService.acceptRide(
                        (long) request.getBookingId(),
                        request.getDriverId()
                );

                responseObserver.onNext(
                        RideAcceptanceResponse.newBuilder()
                                .setSuccess(success)
                                .build()
                );
                responseObserver.onCompleted();

            } catch (IllegalArgumentException e) {
                // BUSINESS ERROR (booking/driver missing)
                responseObserver.onError(
                        io.grpc.Status.NOT_FOUND
                                .withDescription(e.getMessage())
                                .asRuntimeException()
                );

            } catch (Exception e) {
                // UNEXPECTED SERVER ERROR
                e.printStackTrace();
                responseObserver.onError(
                        io.grpc.Status.INTERNAL
                                .withDescription("Accept ride failed")
                                .asRuntimeException()
                );
            }
        }

    }

