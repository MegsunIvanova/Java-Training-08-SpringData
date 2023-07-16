package bg.softuni.cardealer.services.impl;

import bg.softuni.cardealer.entities.customers.Customer;
import bg.softuni.cardealer.entities.customers.CustomerOrderedDto;
import bg.softuni.cardealer.repositories.CustomersRepository;
import bg.softuni.cardealer.services.CustomersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersServiceImpl implements CustomersService {
    private final ModelMapper mapper;
    private final CustomersRepository customersRepository;

    public CustomersServiceImpl(ModelMapper mapper, CustomersRepository customersRepository) {
        this.mapper = mapper;
        this.customersRepository = customersRepository;
    }

    @Override
    public List<CustomerOrderedDto> getAllCustomersOrderedByBirthdate() {
        List<Customer> customers = this.customersRepository
                .findAllByOrderByBirthDateAscYoungDriverAsc();

        return customers
                .stream()
                .map(customer -> mapper.map(customer, CustomerOrderedDto.class))
                .toList();
    }
}
