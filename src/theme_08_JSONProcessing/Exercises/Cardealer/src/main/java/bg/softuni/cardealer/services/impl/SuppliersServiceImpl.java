package bg.softuni.cardealer.services.impl;

import bg.softuni.cardealer.entities.suppliers.LocalSupplierDto;
import bg.softuni.cardealer.repositories.SuppliersRepository;
import bg.softuni.cardealer.services.SuppliersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    private final ModelMapper mapper;
    private final SuppliersRepository suppliersRepository;

    public SuppliersServiceImpl(ModelMapper mapper, SuppliersRepository suppliersRepository) {
        this.mapper = mapper;
        this.suppliersRepository = suppliersRepository;
    }

    @Override
    public List<LocalSupplierDto> getLocalSuppliersWithPartsCount() {
        return this.suppliersRepository.findAllByNotIsImporter();
    }
}
