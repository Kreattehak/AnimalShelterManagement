package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/api")
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
    @ResponseStatus(CREATED)
    public Address saveAddress(@Valid @RequestBody Address address, @PathVariable Long personId) {
        return addressService.saveAddress(address, personId);
    }

    @PutMapping(value = "${rest.address.putAddress}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Address updateAddress(@Valid @RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    @DeleteMapping(value = "${rest.address.deleteAddress}")
    public void deleteAddress(@PathVariable Long addressId, @PathVariable Long personId) {
        addressService.deleteAddress(addressId, personId);
    }
}