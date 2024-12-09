package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.AddressNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.AddressMapper;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.AddressRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService
{
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    private final AddressMapper addressMapper=new AddressMapper();

    @Override
    public JsonResponseDTO addAddress(String email,Address address)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not Found"));
        Address newAddress=addressMapper.addAddress(user.getUserId(),address);

        if(user.getAddresses()==null)
            user.setAddresses(new ArrayList<>());
        user.getAddresses().add(newAddress);

        Address savedAddress=addressRepository.save(newAddress);
        return addressMapper.saveAddress(savedAddress);
    }

    @Override
    public JsonResponseDTO editAddress(String email,Long addressId, Address address)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not Found!!"));

        Address oldAddress=addressRepository.findById(addressId).orElseThrow(()->new AddressNotFoundException("Address Not Found"));

        if(!user.getAddresses().contains(oldAddress))
            throw new RuntimeException("User does not have this address");

        Address newAddress=addressMapper.editAddress(oldAddress,address);

        return addressMapper.saveAddress(addressRepository.save(newAddress));
    }

    @Override
    public JsonResponseDTO deleteAddress(String email,Long addressId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found!!"));
        Address address=addressRepository.findById(addressId).orElseThrow(()->new AddressNotFoundException("Address not Found"));

        if(!user.getAddresses().contains(address))
            throw new RuntimeException("User does not have this address");

        user.getAddresses().remove(address);
        addressRepository.deleteById(addressId);

        return addressMapper.deleteAddress();
    }

    @Override
    public JsonResponseDTO getAllUserAddress(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        List<Address> addresses=user.getAddresses();
        return addressMapper.sendList(addresses);
    }
}
