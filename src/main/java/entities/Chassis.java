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

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "Chassis")
@NamedQueries({
    @NamedQuery(name = "Chassis.findAll", query = "SELECT c FROM Chassis c"),
    @NamedQuery(name = "Chassis.findByid", query = "SELECT c FROM Chassis c WHERE c.id = :id"),
    @NamedQuery(name = "Chassis.findByModelName", query = "SELECT c FROM Chassis c WHERE c.modelName = :modelName"),
    @NamedQuery(name = "Chassis.findByManufacturer", query = "SELECT c FROM Chassis c WHERE c.manufacturer = :manufacturer"),
    @NamedQuery(name = "Chassis.findByPrice", query = "SELECT c FROM Chassis c WHERE c.price = :price"),
    @NamedQuery(name = "Chassis.findByHeight", query = "SELECT c FROM Chassis c WHERE c.height = :height"),
    @NamedQuery(name = "Chassis.findByWidth", query = "SELECT c FROM Chassis c WHERE c.width = :width"),
    @NamedQuery(name = "Chassis.findByDepth", query = "SELECT c FROM Chassis c WHERE c.depth = :depth"),
    @NamedQuery(name = "Chassis.findByRating", query = "SELECT c FROM Chassis c WHERE c.rating = :rating"),
    @NamedQuery(name = "Chassis.deleteAll", query = "DELETE FROM Chassis"),
    @NamedQuery(name = "Chassis.findByDescription", query = "SELECT c FROM Chassis c WHERE c.description = :description")})
public class Chassis implements Serializable {

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
    @Column(name = "height")
    private Double height;
    @Column(name = "width")
    private Double width;
    @Column(name = "depth")
    private Double depth;
    @Column(name = "rating")
    private Double rating;
    @Size(max = 200)
    @Column(name = "description")
    private String description;

    public Chassis() {
    }

    public Chassis(String modelName, String manufacturer, Double price, Double rating, Double height, Double width, Double depth, String description) {

        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.rating = rating;
        this.description = description;
    }

    public Chassis(Long id) {
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
