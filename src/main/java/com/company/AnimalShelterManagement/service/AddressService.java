package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;

public interface AddressService {

    Iterable<Address> returnAddresses();

    Address returnAddress(Long addressId);

    Address saveAddress(Address address);

    Address updateAddress(Address address);

    void deleteAddress(Long addressId);
}
