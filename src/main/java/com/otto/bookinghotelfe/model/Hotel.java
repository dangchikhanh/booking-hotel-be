package com.otto.bookinghotelfe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelPhone;
    private String hotelMail;
    private String hotelImages;

}

