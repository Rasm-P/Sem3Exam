package dto;

import entities.Chassis;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class ChassisDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "Versa H15 Micro ATX")
    private String modelName;
    @Schema(required = true, example = "Thermaltake")
    private String manufacturer;
    @Schema(required = true, example = "44.99")
    private Double price;
    @Schema(required = true, example = "4.4")
    private Double rating;
    @Schema(required = true, example = "38")
    private Double height;
    @Schema(required = true, example = "19.81")
    private Double width;
    @Schema(required = true, example = "41.14")
    private Double depth;
    @Schema(required = true, example = "Thermaltake Versa H15 micro case is ideal for home-computer builders and gamers, creating enough space for high-end hardware and expansion.")
    private String description;

    public ChassisDTO(Chassis chassis) {
        this.id = chassis.getid();
        this.modelName = chassis.getModelName();
        this.manufacturer = chassis.getManufacturer();
        this.price = chassis.getPrice();
        this.height = chassis.getHeight();
        this.width = chassis.getWidth();
        this.depth = chassis.getDepth();
        this.rating = chassis.getRating();
        this.description = chassis.getDescription();
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
