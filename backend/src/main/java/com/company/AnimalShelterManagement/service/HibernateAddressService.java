package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AddressRepository;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateAddressService extends HibernateCommonService<Address, AddressRepository>
        implements AddressService {

    private final PersonService personService;

    @Autowired
    public HibernateAddressService(AddressRepository addressRepository, PersonService personService) {
        super(Address.class);
        this.repository = addressRepository;
        this.personService = personService;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Address> returnPersonAddresses(Long personId) {
        return repository.findAddressesByPersonId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public Address returnAddress(Long addressId) {
        return ifExistsReturnEntity(addressId);
    }

    @Override
    public Address saveAddress(Address address, Long personId) {
        Person person = personService.returnPerson(personId);
        address = repository.save(address);
        person.addAddress(address);

        return address;
    }

    @Override
    public Address updateAddress(Address address) {
        Address addressFromDb = returnAddress(address.getId());
        addressFromDb.setStreetName(address.getStreetName());
        addressFromDb.setCityName(address.getCityName());
        addressFromDb.setZipCode(address.getZipCode());

        return repository.save(addressFromDb);
    }

    @Override
    public void deleteAddress(Long addressId, Long personId) {
        Person person = personService.returnPerson(personId);
        Address address = returnAddress(addressId);
        if (person.getMainAddress() == address) {
            throw new ProcessUserRequestException(Address.class,
                    "address_id", addressId.toString(), "person_id", personId.toString());
        }
        person.removeAddress(address);

        repository.delete(addressId);
    }
}