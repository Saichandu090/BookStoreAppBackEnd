package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;

import java.util.List;

public class AddressMapper
{
    public Address addAddress(Long userId,Address address)
    {
        return Address.builder()
                .streetName(address.getStreetName())
                .city(address.getCity())
                .pinCode(address.getPinCode())
                .state(address.getState())
                .userId(userId).build();
    }

    public JsonResponseDTO saveAddress(Address address)
    {
        return JsonResponseDTO.builder()
                .data(List.of(address))
                .result(true)
                .message("Address added Successfully!!").build();
    }

    public Address editAddress(Address oldAddress,Address address)
    {
        oldAddress.setStreetName(address.getStreetName());
        oldAddress.setCity(address.getCity());
        oldAddress.setState(address.getState());
        oldAddress.setPinCode(address.getPinCode());
        return oldAddress;
    }

    public JsonResponseDTO deleteAddress()
    {
        return JsonResponseDTO.builder()
                .data(null)
                .result(true)
                .message("Address deleted Successfully!!").build();
    }

    public JsonResponseDTO sendList(List<Address> addresses)
    {
        return JsonResponseDTO.builder()
                .data(addresses)
                .result(true)
                .message("Address retrieved Successfully!!").build();
    }


    public JsonResponseDTO addressNotFound(String message)
    {
        return JsonResponseDTO.builder()
                .data(null)
                .result(false)
                .message(message).build();
    }
}
