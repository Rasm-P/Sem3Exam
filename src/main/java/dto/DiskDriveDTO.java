package dto;

import entities.DiskDrive;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class DiskDriveDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "SSD Master 9000")
    private String modelName;
    @Schema(required = true, example = "King's Way")
    private String manufacturer;
    @Schema(required = true, example = "2000.0")
    private Double price;
    @Schema(required = true, example = "0.1")
    private Double rating;
    @Schema(required = true, example = "5")
    private Integer capacity;
    @Schema(required = true, example = "SSD")
    private String type;
    @Schema(required = true, example = "It can store things and stuff")
    private String description;

    public DiskDriveDTO(DiskDrive diskDrive) {
        this.id = diskDrive.getid();
        this.modelName = diskDrive.getModelName();
        this.manufacturer = diskDrive.getManufacturer();
        this.price = diskDrive.getPrice();
        this.rating = diskDrive.getRating();
        this.capacity = diskDrive.getCapacity();
        this.type = diskDrive.getType();
        this.description = diskDrive.getDescription();
    }

    public long getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
