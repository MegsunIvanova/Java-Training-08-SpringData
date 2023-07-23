package exam.model.dto;

import exam.model.entity.WarrantyType;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ImportLaptopDTO {

    @NotNull
    @Size(min = 9)
    private String macAddress;

    @NotNull
    @Positive
    private Double cpuSpeed;

    @NotNull
    @Min(8)
    @Max((128))
    private Integer ram;

    @NotNull
    @Min(128)
    @Max((1024))
    private Integer storage;

    @NotNull
    @Size(min = 10)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private String warrantyType;

    private ImportLaptopShopDTO shop;

    public ImportLaptopDTO() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ImportLaptopShopDTO getShop() {
        return shop;
    }

    public void setShop(ImportLaptopShopDTO shop) {
        this.shop = shop;
    }
}
