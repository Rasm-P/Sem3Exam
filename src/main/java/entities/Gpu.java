package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "GPU")
@NamedQueries({
    @NamedQuery(name = "Gpu.findAll", query = "SELECT g FROM Gpu g"),
    @NamedQuery(name = "Gpu.findByid", query = "SELECT g FROM Gpu g WHERE g.id = :id"),
    @NamedQuery(name = "Gpu.findByModelName", query = "SELECT g FROM Gpu g WHERE g.modelName = :modelName"),
    @NamedQuery(name = "Gpu.findByManufacturer", query = "SELECT g FROM Gpu g WHERE g.manufacturer = :manufacturer"),
    @NamedQuery(name = "Gpu.findByPrice", query = "SELECT g FROM Gpu g WHERE g.price = :price"),
    @NamedQuery(name = "Gpu.findByMemory", query = "SELECT g FROM Gpu g WHERE g.memory = :memory"),
    @NamedQuery(name = "Gpu.findByBaseClockSpeed", query = "SELECT g FROM Gpu g WHERE g.baseClockSpeed = :baseClockSpeed"),
    @NamedQuery(name = "Gpu.findByBoostClockSpeed", query = "SELECT g FROM Gpu g WHERE g.boostClockSpeed = :boostClockSpeed"),
    @NamedQuery(name = "Gpu.findByPowerConsumption", query = "SELECT g FROM Gpu g WHERE g.powerConsumption = :powerConsumption"),
    @NamedQuery(name = "Gpu.findByDescription", query = "SELECT g FROM Gpu g WHERE g.description = :description"),
    @NamedQuery(name = "Gpu.deleteAll", query = "DELETE FROM Gpu"),
    @NamedQuery(name = "Gpu.findByRating", query = "SELECT g FROM Gpu g WHERE g.rating = :rating")})
public class Gpu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 200)
    @Column(name = "modelName")
    private String modelName;
    @Size(max = 200)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "price")
    private Double price;
    @Column(name = "memory")
    private Integer memory;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "baseClockSpeed")
    private Double baseClockSpeed;
    @Column(name = "boostClockSpeed")
    private Double boostClockSpeed;
    @Column(name = "powerConsumption")
    private Integer powerConsumption;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Double rating;

    public Gpu() {
    }

    public Gpu(String modelName, String manufacturer, Double price, Double rating, Integer memory, Double baseClockSpeed, Double boostClockSpeed, Integer powerConsumption, String description) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.memory = memory;
        this.baseClockSpeed = baseClockSpeed;
        this.boostClockSpeed = boostClockSpeed;
        this.powerConsumption = powerConsumption;
        this.description = description;
        this.rating = rating;
    }

    public Gpu(Long id) {
        this.id = id;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
