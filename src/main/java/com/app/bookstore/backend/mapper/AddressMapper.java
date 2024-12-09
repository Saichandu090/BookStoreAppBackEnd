package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;

import java.util.List;

public class AddressMapper
{
    public Address addAddress(Long userId,Address address)
    {
        Address address1=new Address();
        address1.setStreetName(address.getStreetName());
        address1.setCity(address.getCity());
        address1.setState(address.getState());
        address1.setPinCode(address.getPinCode());
        address1.setUserId(userId);
        return address1;
    }

    public JsonResponseDTO saveAddress(Address address)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Address added Successfully!!");
        responseDTO.setData(List.of(address));
        return responseDTO;
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
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Address deleted Successfully!!");
        responseDTO.setData(null);
        return responseDTO;
    }
}
