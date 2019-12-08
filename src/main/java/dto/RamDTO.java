package dto;

import entities.Ram;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class RamDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "Vengeance RGB PRO")
    private String modelName;
    @Schema(required = true, example = "Corsair")
    private String manufacturer;
    @Schema(required = true, example = "88.99")
    private Double price;
    @Schema(required = true, example = "4.6")
    private Double rating;
    @Schema(required = true, example = "16")
    private Integer capacity;
    @Schema(required = true, example = "3200.0")
    private Double speed;
    @Schema(required = true, example = "CORSAIR VENGEANCE RGB PRO Series DDR4 memory lights up your PC with mesmerizing dynamic multi zone RGB lighting")
    private String description;

    public RamDTO(Ram ram) {
        this.id = ram.getid();
        this.modelName = ram.getModelName();
        this.manufacturer = ram.getManufacturer();
        this.price = ram.getPrice();
        this.rating = ram.getRating();
        this.capacity = ram.getCapacity();
        this.speed = ram.getSpeed();
        this.description = ram.getDescription();
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
