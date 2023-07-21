package bg.softuni.cardealer.services;

import bg.softuni.cardealer.entities.suppliers.LocalSupplierDto;

import java.util.List;

public interface SuppliersService {

    List<LocalSupplierDto> getLocalSuppliersWithPartsCount();


}
