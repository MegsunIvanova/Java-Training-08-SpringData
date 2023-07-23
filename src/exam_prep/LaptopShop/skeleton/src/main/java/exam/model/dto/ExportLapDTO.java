package exam.model.dto;

import exam.model.entity.Shop;

import java.math.BigDecimal;

public class ExportLapDTO {

    private String macAddress;

    private Double cpuSpeed;

    private Integer ram;

    private Integer storage;

    private BigDecimal price;

    private String shopName;
    private String shopTownName;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTownName() {
        return shopTownName;
    }

    public void setShopTownName(String shopTownName) {
        this.shopTownName = shopTownName;
    }

    @Override
    public String toString() {
        return String.format("Laptop - %s%n" +
                "*Cpu speed - %.2f%n" +
                "**Ram - %d%n" +
                "***Storage - %d%n" +
                "****Price - %.2f%n" +
                "#Shop name - %s%n" +
                "##Town - %s%n",
                this.macAddress,
                this.cpuSpeed,
                this.ram,
                this.storage,
                this.price,
                this.shopName,
                this.shopTownName);
    }
}
