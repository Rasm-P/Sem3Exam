package dto;

import entities.Cooling;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class CoolingDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "Hydro Series H80i v2")
    private String modelName;
    @Schema(required = true, example = "Corsair")
    private String manufacturer;
    @Schema(required = true, example = "113.89")
    private Double price;
    @Schema(required = true, example = "4.2")
    private Double rating;
    @Schema(required = true, example = "Hydro Series H80i v2 is an all-in-one liquid CPU cooler with an extra-thick 120mm radiator and dual SP120L PWM fans for cooling that's far superior to stock CPU cooling fans.")
    private String description;
    @Schema(required = true, example = "Water")
    private String type;

    public CoolingDTO(Cooling cooling) {
        this.id = cooling.getid();
        this.modelName = cooling.getModelName();
        this.manufacturer = cooling.getManufacturer();
        this.price = cooling.getPrice();
        this.rating = cooling.getRating();
        this.description = cooling.getDescription();
        this.type = cooling.getType();
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
