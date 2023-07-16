package bg.softuni.cardealer.services.impl;

import bg.softuni.cardealer.entities.Sales.Sale;
import bg.softuni.cardealer.entities.Sales.SaleImportDto;
import bg.softuni.cardealer.entities.cars.Car;
import bg.softuni.cardealer.entities.cars.CarImportDto;
import bg.softuni.cardealer.entities.customers.Customer;
import bg.softuni.cardealer.entities.customers.CustomerImportDto;
import bg.softuni.cardealer.entities.parts.Part;
import bg.softuni.cardealer.entities.parts.PartImportDto;
import bg.softuni.cardealer.entities.suppliers.Supplier;
import bg.softuni.cardealer.entities.suppliers.SupplierImportDto;
import bg.softuni.cardealer.repositories.*;
import bg.softuni.cardealer.services.SeedService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String SUPPLIERS_JSON_PATH = "src/main/resources/cardealer/suppliers.json";
    private static final String CARS_JSON_PATH = "src/main/resources/cardealer/cars.json";
    private static final String CUSTOMERS_JSON_PATH = "src/main/resources/cardealer/customers.json";
    private static final String PARTS_JSON_PATH = "src/main/resources/cardealer/parts.json";

    private static final double[] DISCOUNTS = new double[]{0, 0.05, 0.10, 0.15, 0.20, 0.30, 0.40, 0.50};
    private static final BigDecimal ADDITIONAL_DISCOUNT_FOR_YOUNGER_DRIVERS = BigDecimal.valueOf(0.05);

    private final Gson gson;
    private final ModelMapper mapper;

    private final SuppliersRepository suppliersRepository;
    private final PartsRepository partsRepository;
    private final CarsRepository carsRepository;
    private final CustomersRepository customersRepository;

    private final SalesRepository salesRepository;

    @Autowired
    public SeedServiceImpl(Gson gson, ModelMapper mapper,
                           SuppliersRepository suppliersRepository, PartsRepository partsRepository,
                           CarsRepository carsRepository, CustomersRepository customersRepository,
                           SalesRepository salesRepository) {
        this.gson = gson;
        this.mapper = mapper;
        this.suppliersRepository = suppliersRepository;
        this.partsRepository = partsRepository;
        this.carsRepository = carsRepository;
        this.customersRepository = customersRepository;
        this.salesRepository = salesRepository;
    }

    @Override
    public void seedSuppliers() throws FileNotFoundException {

        FileReader fileReader = new FileReader(SUPPLIERS_JSON_PATH);

        SupplierImportDto[] supplierImportDTOs =
                this.gson.fromJson(fileReader, SupplierImportDto[].class);

        List<Supplier> suppliers = Arrays.stream(supplierImportDTOs)
                .map(dto -> this.mapper.map(dto, Supplier.class))
                .collect(Collectors.toList());

        this.suppliersRepository.saveAll(suppliers);
    }

    @Override
    @Transactional
    public void seedParts() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PARTS_JSON_PATH);

        PartImportDto[] partImportDTOs = this.gson.fromJson(fileReader, PartImportDto[].class);

        List<Supplier> suppliers = this.suppliersRepository.findAll();
        Random random = new Random();

        List<Part> parts = Arrays.stream(partImportDTOs)
                .map(dto -> this.mapper.map(dto, Part.class))
                .peek(part -> part.setSupplier(suppliers.get(random.nextInt(suppliers.size()))))
                .collect(Collectors.toList());

        this.partsRepository.saveAll(parts);
    }


    @Override
    public void seedCars() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CARS_JSON_PATH);

        CarImportDto[] carImportDTOs = this.gson.fromJson(fileReader, CarImportDto[].class);

        List<Part> allParts = this.partsRepository.findAll();

        Random random = new Random();

        List<Car> cars = Arrays.stream(carImportDTOs)
                .map(dto -> this.mapper.map(dto, Car.class))
                .peek(car -> {
                    int size = random.nextInt(2, 6);
                    List<Part> parts = new ArrayList<>();

                    for (int i = 0; i < size; i++) {
                        parts.add(allParts.get(random.nextInt(allParts.size())));
                    }

                    car.setParts(parts);

                }).collect(Collectors.toList());

        this.carsRepository.saveAll(cars);
    }

    @Override
    public void seedCustomers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CUSTOMERS_JSON_PATH);

        CustomerImportDto[] customerImportDTOs = this.gson.fromJson(fileReader, CustomerImportDto[].class);

        TypeMap<CustomerImportDto, Customer> typeMap = mapper.createTypeMap(CustomerImportDto.class, Customer.class);

        typeMap.addMappings(m -> m.map(CustomerImportDto::getBirthDateAsDate, Customer::setBirthDate));

        List<Customer> customers = Arrays.stream(customerImportDTOs)
                .map(typeMap::map)
                .collect(Collectors.toList());

        this.customersRepository.saveAll(customers);
    }

    @Override
    @Transactional
    public void seedSales() {
        Random random = new Random();
        List<Car> allCars = this.carsRepository.findAll();

        List<Customer> allCustomers = this.customersRepository.findAll();

        int numberOfSales = (int) (allCars.size() * random.nextDouble(0.1, 0.8));

        List<SaleImportDto> salesDTOs = new ArrayList<>();

        for (int i = 0; i < numberOfSales; i++) {
            Car car = allCars.get(random.nextInt(allCars.size()));
            allCars.remove(car);

            Customer customer = allCustomers.get(random.nextInt(allCustomers.size()));

            BigDecimal discountPercentage =
                    BigDecimal.valueOf(DISCOUNTS[random.nextInt(DISCOUNTS.length)]);

            if (customer.isYoungDriver()) {
                discountPercentage.add(ADDITIONAL_DISCOUNT_FOR_YOUNGER_DRIVERS);
            }

            BigDecimal carPrice = car.price();

            BigDecimal discountAmount = carPrice.multiply(discountPercentage);

            BigDecimal priceWithDiscount = carPrice.subtract(discountAmount);

            salesDTOs.add(new SaleImportDto(car, customer, carPrice,
                    discountPercentage, discountAmount, priceWithDiscount));
        }

        List<Sale> sales = salesDTOs.stream()
                .map(dto -> mapper.map(dto, Sale.class))
                .collect(Collectors.toList());

        salesRepository.saveAll(sales);
    }

}
