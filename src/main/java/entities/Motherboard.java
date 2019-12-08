package entities;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "Motherboard")

@NamedQueries({
    @NamedQuery(name = "Motherboard.findAll", query = "SELECT m FROM Motherboard m"),
    @NamedQuery(name = "Motherboard.findByid", query = "SELECT m FROM Motherboard m WHERE m.id = :id"),
    @NamedQuery(name = "Motherboard.findByModelName", query = "SELECT m FROM Motherboard m WHERE m.modelName = :modelName"),
    @NamedQuery(name = "Motherboard.findByManufacturer", query = "SELECT m FROM Motherboard m WHERE m.manufacturer = :manufacturer"),
    @NamedQuery(name = "Motherboard.findByPrice", query = "SELECT m FROM Motherboard m WHERE m.price = :price"),
    @NamedQuery(name = "Motherboard.findByHDMISlots", query = "SELECT m FROM Motherboard m WHERE m.HDMISlots = :HDMISlots"),
    @NamedQuery(name = "Motherboard.findByUSBSlots", query = "SELECT m FROM Motherboard m WHERE m.USBSlots = :USBSlots"),
    @NamedQuery(name = "Motherboard.findByRAMSlots", query = "SELECT m FROM Motherboard m WHERE m.RAMSlots = :RAMSlots"),
    @NamedQuery(name = "Motherboard.findByNetworkInterfaceController", query = "SELECT m FROM Motherboard m WHERE m.networkInterfaceController = :networkInterfaceController"),
    @NamedQuery(name = "Motherboard.findByDescription", query = "SELECT m FROM Motherboard m WHERE m.description = :description"),
    @NamedQuery(name = "Motherboard.findBySATASlots", query = "SELECT m FROM Motherboard m WHERE m.SATASlots = :SATASlots"),
    @NamedQuery(name = "Motherboard.findByM2Slots", query = "SELECT m FROM Motherboard m WHERE m.M2Slots = :M2Slots"),
    @NamedQuery(name = "Motherboard.deleteAll", query = "DELETE FROM Motherboard"),
    @NamedQuery(name = "Motherboard.findByRating", query = "SELECT m FROM Motherboard m WHERE m.rating = :rating")})

public class Motherboard implements Serializable {

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
    @Column(name = "HDMISlots")
    private Integer HDMISlots;
    @Column(name = "USBSlots")
    private Integer USBSlots;
    @Column(name = "RAMSlots")
    private Integer RAMSlots;
    @Size(max = 200)
    @Column(name = "networkInterfaceController")
    private String networkInterfaceController;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "SATASlots")
    private Integer SATASlots;
    @Column(name = "M2Slots")
    private Integer M2Slots;
    @Column(name = "rating")
    private Double rating;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Cpu> compatibleCPUList = new ArrayList();
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Gpu> compatibleGPUList = new ArrayList();

    public Motherboard() {
    }

    public Motherboard(String modelName, String manufacturer, Double price, Integer HDMISlots, Integer USBSlots, Integer RAMSlots, String networkInterfaceController, String description, Integer SATASlots, Integer M2Slots, Double rating) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.HDMISlots = HDMISlots;
        this.USBSlots = USBSlots;
        this.RAMSlots = RAMSlots;
        this.networkInterfaceController = networkInterfaceController;
        this.description = description;
        this.SATASlots = SATASlots;
        this.M2Slots = M2Slots;
        this.rating = rating;
    }

    public Motherboard(Long id) {
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

    public Integer getHDMISlots() {
        return HDMISlots;
    }

    public void setHDMISlots(Integer HDMISlots) {
        this.HDMISlots = HDMISlots;
    }

    public Integer getUSBSlots() {
        return USBSlots;
    }

    public void setUSBSlots(Integer USBSlots) {
        this.USBSlots = USBSlots;
    }

    public Integer getRAMSlots() {
        return RAMSlots;
    }

    public void setRAMSlots(Integer RAMSlots) {
        this.RAMSlots = RAMSlots;
    }

    public String getNetworkInterfaceController() {
        return networkInterfaceController;
    }

    public void setNetworkInterfaceController(String networkInterfaceController) {
        this.networkInterfaceController = networkInterfaceController;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSATASlots() {
        return SATASlots;
    }

    public void setSATASlots(Integer SATASlots) {
        this.SATASlots = SATASlots;
    }

    public Integer getM2Slots() {
        return M2Slots;
    }

    public void setSlots(Integer slots) {
        this.M2Slots = slots;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Cpu> getCompatibleCPUList() {
        return compatibleCPUList;
    }

    public void setCompatibleCPUList(List<Cpu> compatibleCPUList) {
        this.compatibleCPUList = compatibleCPUList;
    }

    public void addToCompatibleCPUList(Cpu cpu) {
        compatibleCPUList.add(cpu);
    }

    public List<Gpu> getCompatibleGPUList() {
        return compatibleGPUList;
    }

    public void setCompatibleGPUList(List<Gpu> compatibleGPUList) {
        this.compatibleGPUList = compatibleGPUList;
    }

    public void addToCompatibleGPUList(Gpu gpu) {
        compatibleGPUList.add(gpu);
    }

}
