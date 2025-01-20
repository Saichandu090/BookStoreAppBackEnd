package com.app.bookstore.backend.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressResponseDTO
{
    private Long addressId;
    private String streetName;
    private String city;
    private String state;
    private int pinCode;
}
