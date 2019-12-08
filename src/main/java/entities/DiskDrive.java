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
import org.apache.commons.lang3.EnumUtils;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "DiskDrive")
@NamedQueries({
    @NamedQuery(name = "DiskDrive.findAll", query = "SELECT d FROM DiskDrive d"),
    @NamedQuery(name = "DiskDrive.findByid", query = "SELECT d FROM DiskDrive d WHERE d.id = :id"),
    @NamedQuery(name = "DiskDrive.findByModelName", query = "SELECT d FROM DiskDrive d WHERE d.modelName = :modelName"),
    @NamedQuery(name = "DiskDrive.findByManufacturer", query = "SELECT d FROM DiskDrive d WHERE d.manufacturer = :manufacturer"),
    @NamedQuery(name = "DiskDrive.findByPrice", query = "SELECT d FROM DiskDrive d WHERE d.price = :price"),
    @NamedQuery(name = "DiskDrive.findByCapacity", query = "SELECT d FROM DiskDrive d WHERE d.capacity = :capacity"),
    @NamedQuery(name = "DiskDrive.findByType", query = "SELECT d FROM DiskDrive d WHERE d.type = :type"),
    @NamedQuery(name = "DiskDrive.findByDescription", query = "SELECT d FROM DiskDrive d WHERE d.description = :description"),
    @NamedQuery(name = "DiskDrive.deleteAll", query = "DELETE FROM DiskDrive"),
    @NamedQuery(name = "DiskDrive.findByRating", query = "SELECT d FROM DiskDrive d WHERE d.rating = :rating")})
public class DiskDrive implements Serializable {

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
    @Column(name = "capacity")
    private Integer capacity;
    @Size(max = 4)
    @Column(name = "type")
    private String type;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Double rating;

    public DiskDrive() {
    }

    public DiskDrive(String modelName, String manufacturer, Double price, Integer capacity, String type, String description, Double rating) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.capacity = capacity;
        this.type = validateType(type);
        this.description = description;
        this.rating = rating;
    }

    public DiskDrive(Long id) {
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
            throw new IllegalArgumentException("Illegal enum inserted in Disk Drive. Type used: " + type);
        }
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

    
    private enum Types{
        SSHD,
        HDD,
        SSD
    }
    
}
