package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Address;

public interface AddressService {

    Iterable<Address> returnPersonAddresses(Long personId);

    Address returnAddress(Long addressId);

    Address saveAddress(Address address, Long personId);

    Address updateAddress(Address address);

    void deleteAddress(Long addressId, Long personId);
}
