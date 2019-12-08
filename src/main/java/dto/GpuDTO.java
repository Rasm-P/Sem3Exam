package dto;

import entities.Gpu;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class GpuDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "NoForce 1080")
    private String modelName;
    @Schema(required = true, example = "NVideo")
    private String manufacturer;
    @Schema(required = true, example = "20.9")
    private Double price;
    @Schema(required = true, example = "5.0")
    private Double rating;
    @Schema(required = true, example = "8")
    private Integer memory;
    @Schema(required = true, example = "1609.0")
    private Double baseClockSpeed;
    @Schema(required = true, example = "1791.0")
    private Double boostClockSpeed;
    @Schema(required = true, example = "90")
    private Integer powerConsumption;
    @Schema(required = true, example = "The name is ironic it seems")
    private String description;

    public GpuDTO(Gpu gpu) {
        this.id = gpu.getid();
        this.modelName = gpu.getModelName();
        this.manufacturer = gpu.getManufacturer();
        this.price = gpu.getPrice();
        this.rating = gpu.getRating();
        this.memory = gpu.getMemory();
        this.baseClockSpeed = gpu.getBaseClockSpeed();
        this.boostClockSpeed = gpu.getBoostClockSpeed();
        this.powerConsumption = gpu.getPowerConsumption();
        this.description = gpu.getDescription();
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

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Double getBaseClockSpeed() {
        return baseClockSpeed;
    }

    public void setBaseClockSpeed(Double baseClockSpeed) {
        this.baseClockSpeed = baseClockSpeed;
    }

    public Double getBoostClockSpeed() {
        return boostClockSpeed;
    }

    public void setBoostClockSpeed(Double boostClockSpeed) {
        this.boostClockSpeed = boostClockSpeed;
    }

    public Integer getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Integer powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
