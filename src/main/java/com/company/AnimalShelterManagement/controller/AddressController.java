package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("${rest.address.getAddresses}")
    public Iterable<Address> returnAddresses() {
        return addressService.returnAddresses();
    }

    @GetMapping("${rest.address.getAddress}")
    public Address returnAddress(@PathVariable Long addressId) {
        return addressService.returnAddress(addressId);
    }

    @PostMapping("${rest.address.postAddress}")
    public Address saveAddress(@Valid @RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @PutMapping("${rest.address.putAddress}")
    public Address updateAddress(@Valid @RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    @DeleteMapping("${rest.address.deleteAddress}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }
}