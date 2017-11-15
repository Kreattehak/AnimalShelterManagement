package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    private Address testAddress;

    @MockBean
    private AddressService addressService;
    @Autowired
    private AddressController addressController;

    @Before
    public void setUp() {
        testAddress = new Address(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE);
    }

    @Test
    public void shouldPerformPersonAddresses() {
        when(addressService.returnPersonAddresses(anyLong())).thenReturn(new ArrayList<>());

        addressController.returnPersonAddresses(ID_VALUE);

        verify(addressService).returnPersonAddresses(anyLong());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldPerformReturnAddress() {
        when(addressService.returnAddress(anyLong())).thenReturn(testAddress);

        addressController.returnAddress(ID_VALUE);

        verify(addressService).returnAddress(anyLong());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldPerformSaveAddress() {
        when(addressService.saveAddress(any(Address.class), anyLong())).thenReturn(testAddress);

        addressController.saveAddress(testAddress, ID_VALUE);

        verify(addressService).saveAddress(any(Address.class), anyLong());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldPerformUpdatePerson() {
        when(addressService.updateAddress(any(Address.class))).thenReturn(testAddress);

        addressController.updateAddress(testAddress);

        verify(addressService).updateAddress(any(Address.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldPerformDeletePerson() {
        addressController.deleteAddress(anyLong(), anyLong());

        verify(addressService).deleteAddress(anyLong(), anyLong());
        verifyNoMoreInteractions(addressService);
    }
}