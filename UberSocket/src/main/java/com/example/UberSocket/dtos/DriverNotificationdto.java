package com.example.UberSocket.dtos;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DriverNotificationdto {
    private String pickUpLocationLatitude;
    private String pickUpLocationLongitude;
    private Integer bookingId;
}
