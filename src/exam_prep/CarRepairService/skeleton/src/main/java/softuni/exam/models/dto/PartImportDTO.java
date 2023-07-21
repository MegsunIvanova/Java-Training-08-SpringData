package softuni.exam.models.dto;

import javax.validation.constraints.*;

public class PartImportDTO {
    //•	part name – accepts char sequence (between 2 to 19 inclusive). The values are unique in the database.
    //•	price – Must be between 10 and 2000 (both numbers are INCLUSIVE).
    //•	quantity – accepts a positive number.

    @NotNull
    @Size(min = 2, max = 19)
    private String partName;

    @NotNull
    @DecimalMin(value = "10.0")
    @DecimalMax(value = "2000.0")
    private Double price;

    @NotNull
    @Positive
    private Integer quantity;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
