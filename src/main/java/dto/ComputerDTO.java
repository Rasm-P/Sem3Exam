package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.Computer;
import entities.DiskDrive;
import entities.Gpu;
import entities.Ram;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author Henning
 */
public class ComputerDTO {

    @Schema(required = true)
    private long id;
    @Schema(required = true, example = "Doomlord Bob's Kekmaster 9k Ultra Power Edition")
    private String modelName;
    private List<GpuDTO> gpuList;
    private List<DiskDriveDTO> diskDriveList;
    private CpuDTO cpu;
    private ChassisDTO chassis;
    private CoolingDTO cooling;
    private MotherboardDTO motherboard;
    private PsuDTO psu;
    private List<RamDTO> ramList;

    public ComputerDTO() {
    }

    public ComputerDTO(Computer computer) {
        this.id = computer.getid();
        this.modelName = computer.getModelName();

        this.gpuList = new ArrayList<>();
        computer.getGpuCollection()
                .entrySet().stream().forEach(o -> {
                    for (int i = 0; i < o.getValue(); i++) {
                        gpuList.add(new GpuDTO(o.getKey()));
                    }
                });

        this.diskDriveList = new ArrayList<>();
        computer.getDiskDriveCollection()
                .entrySet().stream().forEach(o -> {
                    for (int i = 0; i < o.getValue(); i++) {
                        diskDriveList.add(new DiskDriveDTO(o.getKey()));
                    }
                });

        this.ramList = new ArrayList<>();
        computer.getRamCollection()
                .entrySet().stream().forEach(o -> {
                    for (int i = 0; i < o.getValue(); i++) {
                        ramList.add(new RamDTO(o.getKey()));
                    }
                });

        if (computer.getCpu() != null) {
            this.cpu = new CpuDTO(computer.getCpu());
        }
        if (computer.getChassis() != null) {
            this.chassis = new ChassisDTO(computer.getChassis());
        }
        if (computer.getCooling() != null) {
            this.cooling = new CoolingDTO(computer.getCooling());
        }
        if (computer.getMotherboard() != null) {
            this.motherboard = new MotherboardDTO(computer.getMotherboard());
        }
        if (computer.getPsu() != null) {
            this.psu = new PsuDTO(computer.getPsu());
        }
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

    public List<GpuDTO> getGpuList() {
        return gpuList;
    }

    public void setGpuList(List<GpuDTO> gpuList) {
        this.gpuList = gpuList;
    }

    public List<DiskDriveDTO> getDiskDriveList() {
        return diskDriveList;
    }

    public void setDiskDriveList(List<DiskDriveDTO> diskDriveList) {
        this.diskDriveList = diskDriveList;
    }

    public CpuDTO getCpu() {
        return cpu;
    }

    public void setCpu(CpuDTO cpu) {
        this.cpu = cpu;
    }

    public ChassisDTO getChassis() {
        return chassis;
    }

    public void setChassis(ChassisDTO chassis) {
        this.chassis = chassis;
    }

    public CoolingDTO getCooling() {
        return cooling;
    }

    public void setCooling(CoolingDTO cooling) {
        this.cooling = cooling;
    }

    public MotherboardDTO getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(MotherboardDTO motherboard) {
        this.motherboard = motherboard;
    }

    public PsuDTO getPsu() {
        return psu;
    }

    public void setPsu(PsuDTO psu) {
        this.psu = psu;
    }

    public List<RamDTO> getRamList() {
        return ramList;
    }

    public void setRamList(List<RamDTO> ramList) {
        this.ramList = ramList;
    }

}
