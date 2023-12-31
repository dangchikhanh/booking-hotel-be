package com.otto.bookinghotelbe.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String phonenumber;
    private String email;
    private String address;
    private String password;
}
