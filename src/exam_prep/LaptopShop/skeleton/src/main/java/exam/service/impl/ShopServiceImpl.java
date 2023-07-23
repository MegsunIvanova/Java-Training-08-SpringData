package exam.service.impl;

import exam.model.dto.ImportShopDTO;
import exam.model.dto.ImportShopWrapperDTO;
import exam.model.dto.ImportTownDTO;
import exam.model.dto.ImportTownWrapperDTO;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtils;
import exam.util.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static exam.model.Constant.*;
import static exam.model.Constant.TOWN;

@Service
public class ShopServiceImpl implements ShopService {

    private final String SHOP_XML_FILE = "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final XMLParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository,
                           TownRepository townRepository, XMLParser xmlParser,
                           ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOP_XML_FILE));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        List<ImportShopDTO> shopDTOs =
                this.xmlParser.fromFile(SHOP_XML_FILE, ImportShopWrapperDTO.class).getShops();

        List<String> resultMessages = new ArrayList<>();

        for (ImportShopDTO dto : shopDTOs) {

            Optional<Shop> shopByName = this.shopRepository.findFirstByName(dto.getName());
            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getTown().getName());

            if (shopByName.isPresent() || townByName.isEmpty() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, SHOP));
                continue;
            }

            Shop entity = modelMapper.map(dto, Shop.class);
            entity.setTown(townByName.get());

            this.shopRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_SHOP,
                    SHOP,
                    entity.getName(),
                    entity.getIncome().setScale(0)));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
