package bg.softuni.cardealer.services;

import bg.softuni.cardealer.entities.customers.CustomerOrderedDto;

import java.util.List;

public interface CustomersService {
    List<CustomerOrderedDto> getAllCustomersOrderedByBirthdate();
}
