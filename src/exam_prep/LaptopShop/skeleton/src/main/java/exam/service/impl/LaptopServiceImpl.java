package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.ExportLapDTO;
import exam.model.dto.ImportCustomerDTO;
import exam.model.dto.ImportLaptopDTO;
import exam.model.entity.*;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.model.Constant.*;
import static exam.model.Constant.CUSTOMER;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final String LAPTOP_JSON_FILE = "src/main/resources/files/json/laptops.json";
    private final ShopRepository shopRepository;
    private final LaptopRepository laptopRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public LaptopServiceImpl(ShopRepository shopRepository, LaptopRepository laptopRepository,
                             Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.laptopRepository = laptopRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_JSON_FILE));
    }

    @Override
    public String importLaptops() throws IOException {
        ImportLaptopDTO[] laptopDTOs =
                gson.fromJson(this.readLaptopsFileContent(), ImportLaptopDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (ImportLaptopDTO dto : laptopDTOs) {
            Optional<Laptop> laptopByMacAddress =
                    this.laptopRepository.findFirstByMacAddress(dto.getMacAddress());

            Optional<Shop> shopByName = this.shopRepository.findFirstByName(dto.getShop().getName());

            Optional<WarrantyType> warrantyType;
            try {
                warrantyType = Optional.of(WarrantyType.valueOf(dto.getWarrantyType()));
            } catch (IllegalArgumentException e) {
                warrantyType = Optional.empty();
            }

            if (warrantyType.isEmpty() || shopByName.isEmpty() ||
                    laptopByMacAddress.isPresent() ||
                    validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, LAPTOP));
                continue;
            }

            Laptop entity = modelMapper.map(dto, Laptop.class);
//            entity.setWarrantyType(warrantyType.get());
            entity.setShop(shopByName.get());

            this.laptopRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_LAPTOP,
                    LAPTOP,
                    entity.getMacAddress(),
                    entity.getCpuSpeed(),
                    entity.getRam(),
                    entity.getStorage()));
        }

        return String.join(System.lineSeparator(), resultMessages);

    }

    @Override
    public String exportBestLaptops() {
        List<Laptop> laptops =
                this.laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddress();

        return laptops.stream()
                .map(laptop -> modelMapper.map(laptop, ExportLapDTO.class))
                .map(ExportLapDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
