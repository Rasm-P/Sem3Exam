package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "CPU")
@NamedQueries({
    @NamedQuery(name = "Cpu.findAll", query = "SELECT c FROM Cpu c"),
    @NamedQuery(name = "Cpu.findByid", query = "SELECT c FROM Cpu c WHERE c.id = :id"),
    @NamedQuery(name = "Cpu.findByModelName", query = "SELECT c FROM Cpu c WHERE c.modelName = :modelName"),
    @NamedQuery(name = "Cpu.findByManufacturer", query = "SELECT c FROM Cpu c WHERE c.manufacturer = :manufacturer"),
    @NamedQuery(name = "Cpu.findByCores", query = "SELECT c FROM Cpu c WHERE c.cores = :cores"),
    @NamedQuery(name = "Cpu.findByThreads", query = "SELECT c FROM Cpu c WHERE c.threads = :threads"),
    @NamedQuery(name = "Cpu.findByClockSpeed", query = "SELECT c FROM Cpu c WHERE c.clockSpeed = :clockSpeed"),
    @NamedQuery(name = "Cpu.findByPowerConsumption", query = "SELECT c FROM Cpu c WHERE c.powerConsumption = :powerConsumption"),
    @NamedQuery(name = "Cpu.findByDescription", query = "SELECT c FROM Cpu c WHERE c.description = :description"),
    @NamedQuery(name = "Cpu.findByPrice", query = "SELECT c FROM Cpu c WHERE c.price = :price"),
    @NamedQuery(name = "Cpu.deleteAll", query = "DELETE FROM Cpu"),
    @NamedQuery(name = "Cpu.findByRating", query = "SELECT c FROM Cpu c WHERE c.rating = :rating")})
public class Cpu implements Serializable {

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
    @Column(name = "cores")
    private Integer cores;
    @Column(name = "threads")
    private Integer threads;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "clock_speed")
    private Double clockSpeed;
    @Column(name = "power_consumption")
    private Integer powerConsumption;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "rating")
    private Double rating;

    public Cpu() {
    }

    public Cpu(String modelName, String manufacturer, Integer cores, Integer threads, Double clockSpeed, Integer powerConsumption, String description, Double price, Double rating) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.cores = cores;
        this.threads = threads;
        this.clockSpeed = clockSpeed;
        this.powerConsumption = powerConsumption;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public Cpu(Long id) {
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


}
