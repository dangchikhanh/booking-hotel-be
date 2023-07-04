package com.otto.bookinghotelbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long bookingId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId")
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
