package com.app.bookstore.backend.service;

import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.AddressRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.requestdto.AddressRequestDTO;
import com.app.bookstore.backend.responsedto.AddressResponseDTO;
import com.app.bookstore.backend.serviceimpl.AddressServiceImpl;
import com.app.bookstore.backend.util.ResponseStructure;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest
{
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @MockBean
    private UserMapper userMapper;

    private Address address;
    private User user;
    private AddressRequestDTO addressRequestDTO;

    @BeforeEach
    public void init()
    {
        addressRequestDTO=AddressRequestDTO.builder()
                .streetName("Baner")
                .city("Pune")
                .pinCode(414004)
                .state("Maharastra").build();

        address=Address.builder()
                .streetName(addressRequestDTO.getStreetName())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .pinCode(addressRequestDTO.getPinCode())
                .addressId(1L)
                .userId(1L).build();

        List<Address> addresses=new ArrayList<>();
        addresses.add(address);

        user=User.builder()
                .userId(1L)
                .firstName("Sai")
                .lastName("Chandu")
                .email("marri@gmail.com")
                .dob(LocalDate.of(2002,8,24))
                .password("saichandu@45")
                .role("USER")
                .addresses(addresses)
                .registeredDate(LocalDate.now()).build();
    }

    @Test
    public void addressService_AddAddress_MustAddAddress()
    {
        when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        ResponseEntity<ResponseStructure<AddressResponseDTO>> response=addressService.addAddress(user.getEmail(),addressRequestDTO);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody().getStatus()).isEqualTo(201);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo(response.getBody().getMessage());
        Assertions.assertThat(response.getBody().getData().getAddressId()).isEqualTo(address.getAddressId());
    }

    @Test
    public void addressService_AddAddress_MustThrowException()
    {
        when(userRepository.findByEmail(Mockito.any(String.class))).thenThrow(new UserNotFoundException("User not Found"));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,()->addressService.addAddress(null,addressRequestDTO));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,()->addressService.addAddress(user.getEmail(),null));
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class,()->addressService.addAddress("Random",addressRequestDTO));
    }



//    @Test
//    public void addressService_EditAddress_MustEditAddress()
//    {
//        when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
//        when(addressRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(address));
//        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);
//
//        JsonResponseDTO responseDTO=addressService.editAddress(user.getEmail(),address.getAddressId(),address);
//        Assertions.assertThat(responseDTO).isNotNull();
//        Assertions.assertThat(responseDTO.isResult()).isEqualTo(true);
//    }
//
//    @Test
//    public void addressService_DeleteAddress_MustDeleteAddress()
//    {
//        when(addressRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(address));
//        when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
//
//        assertAll(()->addressService.deleteAddress(user.getEmail(),address.getAddressId()));
//    }
//
//    @Test
//    public void addressService_GetAllUserAddress_MustReturnAllUserAddress()
//    {
//        when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
//
//        JsonResponseDTO responseDTO=addressService.getAllUserAddress(user.getEmail());
//        Assertions.assertThat(responseDTO).isNotNull();
//        Assertions.assertThat(responseDTO.isResult()).isEqualTo(true);
//    }
//
//    @Test
//    public void addressService_GetAddressById_MustReturnAddressById()
//    {
//        when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
//        when(addressRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(address));
//
//        JsonResponseDTO responseDTO=addressService.getAddressById(user.getEmail(),address.getAddressId());
//        Assertions.assertThat(responseDTO).isNotNull();
//        Assertions.assertThat(responseDTO.isResult()).isEqualTo(true);
//    }
}