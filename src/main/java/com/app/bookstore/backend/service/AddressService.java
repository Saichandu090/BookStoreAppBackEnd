package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;

public interface AddressService
{
    JsonResponseDTO addAddress(String email,Address address);

    JsonResponseDTO editAddress(String email,Long addressId,Address address);

    JsonResponseDTO deleteAddress(String email,Long addressId);

    JsonResponseDTO getAllUserAddress(String email);

    JsonResponseDTO getAddressById(String email,Long addressId);
}
