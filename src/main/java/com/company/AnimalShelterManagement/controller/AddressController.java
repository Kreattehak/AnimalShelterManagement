package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(value = "${rest.address.getAddresses}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Address> returnPersonAddresses(@PathVariable Long personId) {
        return addressService.returnPersonAddresses(personId);
    }

    @GetMapping(value = "${rest.address.getAddress}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Address returnAddress(@PathVariable Long addressId) {
        return addressService.returnAddress(addressId);
    }

    @PostMapping(value = "${rest.address.postAddress}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Address saveAddress(@Valid @RequestBody Address address, @PathVariable Long personId) {
        return addressService.saveAddress(address, personId);
    }

    @PutMapping(value = "${rest.address.putAddress}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Address updateAddress(@Valid @RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    @DeleteMapping(value = "${rest.address.deleteAddress}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }
}