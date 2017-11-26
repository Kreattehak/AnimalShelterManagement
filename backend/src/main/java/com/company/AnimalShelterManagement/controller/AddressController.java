package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_UTF8_VALUE)
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("${rest.address.getAddresses}")
    public Iterable<Address> returnPersonAddresses(@PathVariable Long personId) {
        return addressService.returnPersonAddresses(personId);
    }

    @GetMapping("${rest.address.getAddress}")
    public Address returnAddress(@PathVariable Long addressId) {
        return addressService.returnAddress(addressId);
    }

    @PostMapping(value = "${rest.address.postAddress}", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public Address saveAddress(@Valid @RequestBody Address address, @PathVariable Long personId) {
        return addressService.saveAddress(address, personId);
    }

    @PutMapping(value = "${rest.address.putAddress}", consumes = APPLICATION_JSON_UTF8_VALUE)
    public Address updateAddress(@Valid @RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    @DeleteMapping("${rest.address.deleteAddress}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId, @PathVariable Long personId) {
        addressService.deleteAddress(addressId, personId);
        return ResponseEntity.status(OK).build();
    }
}