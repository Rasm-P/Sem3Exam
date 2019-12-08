package dto;

import entities.Cpu;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class CpuDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "Kekmaster 6900 LuL Lake Ultra")
    private String modelName;
    @Schema(required = true, example = "Outtel")
    private String manufacturer;
    @Schema(required = true, example = "69.0")
    private Double price;
    @Schema(required = true, example = "4.1")
    private Double rating;
    @Schema(required = true, example = "4")
    private Integer cores;
    @Schema(required = true, example = "8")
    private Integer threads;
    @Schema(required = true, example = "20.9")
    private Double clockSpeed;
    @Schema(required = true, example = "2000")
    private Integer powerConsumption;
    @Schema(required = true, example = "Only for the most ultra of keklords")
    private String description;

    public CpuDTO(Cpu cpu) {
        this.id = cpu.getid();
        this.modelName = cpu.getModelName();
        this.manufacturer = cpu.getManufacturer();
        this.price = cpu.getPrice();
        this.rating = cpu.getRating();
        this.cores = cpu.getCores();
        this.threads = cpu.getThreads();
        this.clockSpeed = cpu.getClockSpeed();
        this.powerConsumption = cpu.getPowerConsumption();
        this.description = cpu.getDescription();
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

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Double getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(Double clockSpeed) {
        this.clockSpeed = clockSpeed;
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
