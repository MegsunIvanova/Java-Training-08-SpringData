package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.ImportCustomerDTO;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static exam.model.Constant.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final String CUSTOMER_JSON_FILE = "src/main/resources/files/json/customers.json";

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Converter<String, LocalDate> localDateConverter;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository,
                               Gson gson, ValidationUtils validationUtils,
                               ModelMapper modelMapper, Converter<String, LocalDate> localDateConverter) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.localDateConverter = localDateConverter;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMER_JSON_FILE));
    }

    @Override
    public String importCustomers() throws IOException {
        ImportCustomerDTO[] customerDTOs =
                gson.fromJson(this.readCustomersFileContent(), ImportCustomerDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        TypeMap<ImportCustomerDTO, Customer> typeMap =
                modelMapper.createTypeMap(ImportCustomerDTO.class, Customer.class);
        typeMap.addMappings(mapper -> mapper.using(localDateConverter)
                .map(ImportCustomerDTO::getRegisteredOn, Customer::setRegisteredOn));


        for (ImportCustomerDTO dto : customerDTOs) {

            Optional<Customer> customerByEmail = this.customerRepository.findByEmail(dto.getEmail());
            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getTown().getName());

            if (customerByEmail.isPresent() || townByName.isEmpty() ||
                    validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, CUSTOMER));
                continue;
            }

            Customer entity = typeMap.map(dto);
            entity.setTown(townByName.get());

            this.customerRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_CUSTOMER,
                    CUSTOMER,
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
