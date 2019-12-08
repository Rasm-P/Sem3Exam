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
@Table(name = "RAM")
@NamedQueries({
    @NamedQuery(name = "Ram.findAll", query = "SELECT r FROM Ram r"),
    @NamedQuery(name = "Ram.findByid", query = "SELECT r FROM Ram r WHERE r.id = :id"),
    @NamedQuery(name = "Ram.findByModelName", query = "SELECT r FROM Ram r WHERE r.modelName = :modelName"),
    @NamedQuery(name = "Ram.findByManufacturer", query = "SELECT r FROM Ram r WHERE r.manufacturer = :manufacturer"),
    @NamedQuery(name = "Ram.findByPrice", query = "SELECT r FROM Ram r WHERE r.price = :price"),
    @NamedQuery(name = "Ram.findByCapacity", query = "SELECT r FROM Ram r WHERE r.capacity = :capacity"),
    @NamedQuery(name = "Ram.findBySpeed", query = "SELECT r FROM Ram r WHERE r.speed = :speed"),
    @NamedQuery(name = "Ram.findByDescription", query = "SELECT r FROM Ram r WHERE r.description = :description"),
    @NamedQuery(name = "Ram.deleteAll", query = "DELETE FROM Ram"),
    @NamedQuery(name = "Ram.findByRating", query = "SELECT r FROM Ram r WHERE r.rating = :rating")})
public class Ram implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 200)
    @Column(name = "modelName")
    private String modelName;
    @Size(max = 200)
    @Column(name = "manufacturer")
    private String manufacturer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "speed")
    private Double speed;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Double rating;

    public Ram() {
    }

    public Ram(String modelName, String manufacturer, Double price, Integer capacity, Double speed, String description, Double rating) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.capacity = capacity;
        this.speed = speed;
        this.description = description;
        this.rating = rating;
    }

    public Ram(Long id) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
