package bg.softuni.productshop_xml.services;

import bg.softuni.productshop_xml.entities.products.ExportProductsInRangeDTO;
import bg.softuni.productshop_xml.entities.products.Product;
import bg.softuni.productshop_xml.entities.products.ProductWithAttributesDTO;
import bg.softuni.productshop_xml.entities.users.User;
import bg.softuni.productshop_xml.repositories.ProductRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;
    private final ProductRepository productRepository;
    private final TypeMap<Product, ProductWithAttributesDTO> productToDtoMapping;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;

        this.mapper = mapper;

        Converter<User, String> userToFullNameConverter =
                context -> context.getSource() == null ? null : context.getSource().getFullName();

        TypeMap<Product, ProductWithAttributesDTO> typeMap =
                this.mapper.createTypeMap(Product.class, ProductWithAttributesDTO.class);

        this.productToDtoMapping = typeMap.addMappings(map ->
                map.using(userToFullNameConverter)
                        .map(Product::getSeller, ProductWithAttributesDTO::setSeller));
    }

    @Override
    public ExportProductsInRangeDTO getInRange(double from, double to) {
        BigDecimal rangeFrom = BigDecimal.valueOf(from);
        BigDecimal rangeTo = BigDecimal.valueOf(to);

        List<Product> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(rangeFrom, rangeTo);

        List<ProductWithAttributesDTO> dTOs =
                products
                        .stream()
                        .map(this.productToDtoMapping::map)
                        .toList();

        return new ExportProductsInRangeDTO(dTOs);
    }
}
