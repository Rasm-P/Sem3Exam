package dto;

import entities.Psu;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class PsuDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "EVGA 750 GQ")
    private String modelName;
    @Schema(required = true, example = "EVGA")
    private String manufacturer;
    @Schema(required = true, example = "94.99")
    private Double price;
    @Schema(required = true, example = "4.5")
    private Double rating;
    @Schema(required = true, example = "750")
    private Integer watts;
    @Schema(required = true, example = "Introducing the latest in the EVGA power supply lineup; the GQ series")
    private String description;

    public PsuDTO(Psu psu) {
        this.id = psu.getid();
        this.modelName = psu.getModelName();
        this.manufacturer = psu.getManufacturer();
        this.price = psu.getPrice();
        this.rating = psu.getRating();
        this.watts = psu.getWatts();
        this.description = psu.getDescription();
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

    public Integer getWatts() {
        return watts;
    }

    public void setWatts(Integer watts) {
        this.watts = watts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
