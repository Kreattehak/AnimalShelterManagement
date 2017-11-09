package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AddressRepository;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class HibernateAddressService implements AddressService {

    private final AddressRepository addressRepository;
    private final PersonService personService;

    @Autowired
    public HibernateAddressService(AddressRepository addressRepository, PersonService personService) {
        this.addressRepository = addressRepository;
        this.personService = personService;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Address> returnPersonAddresses(Long personId) {
        return addressRepository.findAddressesByPersonId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public Address returnAddress(Long addressId) {
        return ifExistsReturnAddress(addressId);
    }

    @Override
    public Address saveAddress(Address address, Long personId) {
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

    private Address ifExistsReturnAddress(Long addressId) {
        Address address = addressRepository.findOne(addressId);
        if (address == null) {
            throw new EntityNotFoundException(Address.class, "addressId", addressId.toString());
        }

        return address;
    }
}