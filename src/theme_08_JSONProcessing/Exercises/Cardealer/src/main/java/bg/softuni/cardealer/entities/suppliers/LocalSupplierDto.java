package bg.softuni.cardealer.entities.suppliers;

public class LocalSupplierDto {

    private Integer id;

    private String name;

    private Long partsCount;

    public LocalSupplierDto(Integer id, String name, Long partsCount) {
        this.id = id;
        this.name = name;
        this.partsCount = partsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Long partsCount) {
        this.partsCount = partsCount;
    }
}
