package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.repository.AddressRepository;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateAddressService implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public HibernateAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Address> returnPersonAddresses(Long personId) {
        return addressRepository.findByPersonId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public Address returnAddress(Long addressId) {
        return addressRepository.findOne(addressId);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.delete(addressId);
    }
}