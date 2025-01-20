package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.requestdto.AddressRequestDTO;
import com.app.bookstore.backend.responsedto.AddressResponseDTO;
import com.app.bookstore.backend.util.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface AddressService
{
    ResponseEntity<ResponseStructure<AddressResponseDTO>> addAddress(String email, AddressRequestDTO addressRequestDTO);

    JsonResponseDTO editAddress(String email,Long addressId,Address address);

    JsonResponseDTO deleteAddress(String email,Long addressId);

    JsonResponseDTO getAllUserAddress(String email);

    JsonResponseDTO getAddressById(String email,Long addressId);
}
