package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "PSU")
@NamedQueries({
    @NamedQuery(name = "Psu.findAll", query = "SELECT p FROM Psu p"),
    @NamedQuery(name = "Psu.findByid", query = "SELECT p FROM Psu p WHERE p.id = :id"),
    @NamedQuery(name = "Psu.findByModelName", query = "SELECT p FROM Psu p WHERE p.modelName = :modelName"),
    @NamedQuery(name = "Psu.findByManufacturer", query = "SELECT p FROM Psu p WHERE p.manufacturer = :manufacturer"),
    @NamedQuery(name = "Psu.findByPrice", query = "SELECT p FROM Psu p WHERE p.price = :price"),
    @NamedQuery(name = "Psu.findByWatts", query = "SELECT p FROM Psu p WHERE p.watts = :watts"),
    @NamedQuery(name = "Psu.findByDescription", query = "SELECT p FROM Psu p WHERE p.description = :description"),
    @NamedQuery(name = "Psu.deleteAll", query = "DELETE FROM Psu"),
    @NamedQuery(name = "Psu.findByRating", query = "SELECT p FROM Psu p WHERE p.rating = :rating")})
public class Psu implements Serializable {

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Column(name = "watts")
    private Integer watts;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Double rating;

    public Psu() {
    }

    public Psu(String modelName, String manufacturer, Double price, Integer watts, String description, Double rating) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.watts = watts;
        this.description = description;
        this.rating = rating;
    }

    public Psu(Long id) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


}
