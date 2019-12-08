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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.EnumUtils;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "Cooling")
@NamedQueries({
    @NamedQuery(name = "Cooling.findAll", query = "SELECT c FROM Cooling c"),
    @NamedQuery(name = "Cooling.findByid", query = "SELECT c FROM Cooling c WHERE c.id = :id"),
    @NamedQuery(name = "Cooling.findByModelName", query = "SELECT c FROM Cooling c WHERE c.modelName = :modelName"),
    @NamedQuery(name = "Cooling.findByManufacturer", query = "SELECT c FROM Cooling c WHERE c.manufacturer = :manufacturer"),
    @NamedQuery(name = "Cooling.findByPrice", query = "SELECT c FROM Cooling c WHERE c.price = :price"),
    @NamedQuery(name = "Cooling.findByRating", query = "SELECT c FROM Cooling c WHERE c.rating = :rating"),
    @NamedQuery(name = "Cooling.deleteAll", query = "DELETE FROM Cooling"),
    @NamedQuery(name = "Cooling.findByType", query = "SELECT c FROM Cooling c WHERE c.type = :type")})
public class Cooling implements Serializable {

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
    @Column(name = "rating")
    private Double rating;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Size(max = 5)
    @Column(name = "type")
    private String type;

    public Cooling() {
    }

    public Cooling(String modelName, String manufacturer, Double price, Double rating, String description, String type) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.type = validateType(type);
    }

    public Cooling(Long id) {
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
        this.type = validateType(type);
    }

    public String validateType(String type) {
        if (EnumUtils.isValidEnum(Types.class, type.toUpperCase())) {
            return type;
        } else {
            throw new IllegalArgumentException("Illegal enum inserted in Cooling. Type used: " + type);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private enum Types {
        WATER,
        AIR
    }
}
