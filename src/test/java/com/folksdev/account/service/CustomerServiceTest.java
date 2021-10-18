package com.folksdev.account.service;

import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.dto.CustomerDtoConverter;
import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CustomerServiceTest {
    //Void deger donen metotlar test edilmez
    //private ve protexted metotlar test edilmez
    //void metotlar dışındaki bütün metotların her bir durumu için AYRI AYRI test seneryoları yazılır
        private CustomerService service;
        private CustomerRepository customerRepository;
        private CustomerDtoConverter converter;

        @BeforeEach
        public void setUp() {
            customerRepository = mock(CustomerRepository.class);
            converter = mock(CustomerDtoConverter.class);
            service = new CustomerService(customerRepository, converter);

        }
        //dışarıya çıktığımız her an when yazarız when ile mock a ne yapacağını öğretiyoruz. Dışarı çıkmak demek örneğin repoya gitmek
        //yani dışardan bir şey kullanacığımız zaman when yapıyoruz.
    //her if else if için yeni test metotları yazmamız lazım
        @Test
        public void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer() {
            Customer customer = new Customer("id","name","surname", Set.of());
            Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

            Customer result = service.findCustomerById("id");

            assertEquals(result,customer);   ///beklenen deger, gelen deger
        }

        @Test
        public void testFindByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {
            Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

            assertThrows(CustomerNotFoundException.class, () -> service.findCustomerById("id"));
        }

        @Test
        public void testGetByCustomerId_whenCustomerIdExists_shouldReturnCustomer() {

            Customer customer = new Customer("id","name","surname", Set.of());
            CustomerDto customerDto = new CustomerDto("id","name","surname",Set.of());

            Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));
            Mockito.when(converter.convertToCustomerDto(customer)).thenReturn(customerDto);
            CustomerDto result = service.getCustomerById("id");

            assertEquals(result,customerDto);


        }

        @Test
        public void testGetByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {

            Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

            assertThrows(CustomerNotFoundException.class,
                    () -> service.getCustomerById("id"));  //bunu çağırdığında bunu getir

            Mockito.verifyNoInteractions(converter);
        }




}