package com.app.bookstore.backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO
{
    private String streetName;
    private String city;
    private String state;
    private int pinCode;
}
