package dto;

import java.util.List;
import entities.Motherboard;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Henning
 */
public class MotherboardDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "ROG Strix B450-F")
    private String modelName;
    @Schema(required = true, example = "ASUS")
    private String manufacturer;
    @Schema(required = true, example = "119.99")
    private Double price;
    @Schema(required = true, example = "4.2")
    private Double rating;
    @Schema(required = true, example = "1")
    private Integer HDMISlots;
    @Schema(required = true, example = "4")
    private Integer USBSlots;
    @Schema(required = true, example = "4")
    private Integer RAMSlots;
    @Schema(required = true, example = "2x2 Wi-Fi with MU-MIMO")
    private String networkInterfaceController;
    @Schema(required = true, example = "Experience next level performance with the ROG Strix B450 F Gaming")
    private String description;
    @Schema(required = true, example = "0")
    private Integer SATASlots;
    @Schema(required = true, example = "1")
    private Integer M2Slots;
    private List<DiskDriveDTO> DiskDriveList;
    private List<GpuDTO> GPUList;
    private List<RamDTO> RAMList;

    public MotherboardDTO(Motherboard motherboard) {
        this.id = motherboard.getid();
        this.modelName = motherboard.getModelName();
        this.manufacturer = motherboard.getManufacturer();
        this.price = motherboard.getPrice();
        this.rating = motherboard.getRating();
        this.HDMISlots = motherboard.getHDMISlots();
        this.USBSlots = motherboard.getUSBSlots();
        this.RAMSlots = motherboard.getRAMSlots();
        this.networkInterfaceController = motherboard.getNetworkInterfaceController();
        this.description = motherboard.getDescription();
        this.SATASlots = motherboard.getSATASlots();
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

    public void setM2Slots(Integer M2Slots) {
        this.M2Slots = M2Slots;
    }

    public List<DiskDriveDTO> getDiskDriveList() {
        return DiskDriveList;
    }

    public void setDiskDriveList(List<DiskDriveDTO> DiskDriveList) {
        this.DiskDriveList = DiskDriveList;
    }

    public List<GpuDTO> getGPUList() {
        return GPUList;
    }

    public void setGPUList(List<GpuDTO> GPUList) {
        this.GPUList = GPUList;
    }

    public List<RamDTO> getRAMList() {
        return RAMList;
    }

    public void setRAMList(List<RamDTO> RAMList) {
        this.RAMList = RAMList;
    }

}
