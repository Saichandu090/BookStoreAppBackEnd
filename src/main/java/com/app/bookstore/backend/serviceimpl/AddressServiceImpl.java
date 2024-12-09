package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService
{
    @Override
    public JsonResponseDTO addAddress(Address address)
    {
        return null;
    }

    @Override
    public JsonResponseDTO editAddress(Long addressId, Address address)
    {
        return null;
    }
}
