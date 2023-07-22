package softuni.exam.models.dto;

import softuni.exam.models.entity.Town;

public class ExportApartmentDTO {

    private Double area;
    private ExportTownDTO town;

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public ExportTownDTO getTown() {
        return town;
    }

    public void setTown(ExportTownDTO town) {
        this.town = town;
    }
}
