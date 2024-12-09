package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;

public interface AddressService
{
    JsonResponseDTO addAddress(Address address);

    JsonResponseDTO editAddress(Long addressId,Address address);
}
