package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "Computer")
@NamedQueries({
    @NamedQuery(name = "Computer.findAll", query = "SELECT c FROM Computer c"),
    @NamedQuery(name = "Computer.deleteAll", query = "DELETE FROM Computer"),
    @NamedQuery(name = "Computer.findByid", query = "SELECT c FROM Computer c WHERE c.id = :id")})
public class Computer implements Serializable {

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
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Computer_Gpu")
    @MapKeyJoinColumn(name = "id")
    @Column(name = "COUNT")
    @JsonIgnore
    private Map<Gpu, Integer> gpuCollection;
    @ElementCollection()
    @CollectionTable(name = "Computer_Disk_Drive")
    @MapKeyJoinColumn(name = "id")
    @Column(name = "COUNT")
    @JsonIgnore
    private Map<DiskDrive, Integer> diskDriveCollection;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Computer_Ram")
    @MapKeyJoinColumn(name = "id")
    @Column(name = "COUNT")
    @JsonIgnore
    private Map<Ram, Integer> ramCollection;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cpu cpu;
    @ManyToOne(fetch = FetchType.LAZY)
    private Chassis Chassis;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cooling Cooling;
    @ManyToOne(fetch = FetchType.LAZY)
    private Motherboard Motherboard;
    @ManyToOne(fetch = FetchType.LAZY)
    private Psu psu;

    @Transient
    List<Ram> ramList;
    @Transient
    List<Gpu> gpuList;
    @Transient
    List<DiskDrive> diskDriveList;

    public Computer() {
    }

    public Computer(String modelName, List<Gpu> gpuList, List<DiskDrive> diskDriveList, List<Ram> ramList, Cpu cPUid, Chassis chassisid, Cooling Cooling, Motherboard motherboardid, Psu pSUid) {
        this.modelName = modelName;
        this.gpuList = gpuList;
        this.diskDriveList = diskDriveList;
        this.ramList = ramList;
        this.cpu = cPUid;
        this.Chassis = chassisid;
        this.Cooling = Cooling;
        this.Motherboard = motherboardid;
        this.psu = pSUid;
    }

    public Computer(String modelName, HashMap<Gpu, Integer> gpuCollection, HashMap<DiskDrive, Integer> diskDriveCollection, HashMap<Ram, Integer> ramCollection, Cpu cPUid, Chassis chassisid, Cooling Cooling, Motherboard motherboardid, Psu pSUid) {
        this.modelName = modelName;
        this.gpuCollection = gpuCollection;
        this.diskDriveCollection = diskDriveCollection;
        this.ramCollection = ramCollection;
        this.cpu = cPUid;
        this.Chassis = chassisid;
        this.Cooling = Cooling;
        this.Motherboard = motherboardid;
        this.psu = pSUid;
    }

    public Computer(Long id) {
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

    public Psu getPsu() {
        return psu;
    }

    public void setPsu(Psu psu) {
        this.psu = psu;
    }

    public List<Gpu> getGpuList() {
        return gpuList;
    }

    public void setGpuList(List<Gpu> gpuList) {
        this.gpuList = gpuList;
    }

    public void addToGpuList(Gpu gpu) {
        gpuList.add(gpu);
    }

    public List<DiskDrive> getDiskDriveList() {
        return diskDriveList;
    }

    public void setDiskDriveList(List<DiskDrive> diskDriveList) {
        this.diskDriveList = diskDriveList;
    }

    public void addToDiskDriveList(DiskDrive diskDrive) {
        diskDriveList.add(diskDrive);
    }

    public Map<Ram, Integer> getRamCollection() {
        return ramCollection;
    }

    public void setRamCollection(Map<Ram, Integer> ramCollection) {
        this.ramCollection = ramCollection;
    }

    public HashMap<Ram, Integer> ramListToCollection(List<Ram> ramList) {
        HashMap<Ram, Integer> ramMap = new HashMap<>();
        HashMap<Ram, Integer> switcheroo = new HashMap<>();

        // This netbeans suggested solutions somehow stops the problem of computer stopping being an entity.
        /*
        The dysfunctional alternative was: 
             ramList.stream().forEach((Ram o) -> {
         */
        ramList.stream().forEach(new Consumer<Ram>() {
            @Override
            public void accept(Ram o) {
                if (ramMap.entrySet().isEmpty()) {
                    ramMap.put(o, 1);
                } //Avoiding out of bounds
                else if (!(ramMap
                        .entrySet()
                        .stream()
                        .anyMatch(j -> j.getKey()
                        .getModelName()
                        .equals(o.getModelName())))) {
                    ramMap.put(o, 1);
                } else {
                    ramMap.entrySet().stream().forEach(j -> {
                        if (j.getKey().getModelName().equals(o.getModelName())) {
                            switcheroo.put(j.getKey(), j.getValue() + 1);
                        }
                    });
                    switcheroo.entrySet().stream().forEach(j -> {
                        ramMap.put(j.getKey(), j.getValue());
                    });
                }
            }
        });
        return ramMap;
    }

    public void addToRamCollection(Ram ram) {
        //Using more primitive approaches, since using lambda expressions somehow makes computer.deletall namedquery stop existing
        //and the project won't build

        boolean checkRamName = false;
        int newNum = 0;

        Entry entry = null;
        for (Entry ent : ramCollection.entrySet()) {
            if (((Ram) ent.getKey()).getModelName().equals(ram.getModelName())) {
                entry = ent;
                newNum = Integer.valueOf(ent.getValue().toString()) + 1;
                checkRamName = true;
            }
        }
        
        if (checkRamName == true) {
            ramCollection.entrySet().remove(entry);
            
            ramCollection.put(ram, newNum);
        } else {
            ramCollection.put(ram, 1);
        }
    }

    public List<Ram> getRamList() {
        return ramList;
    }

    public void setRamList(List<Ram> ramList) {
        this.ramList = ramList;
    }

    public void addToRamList(Ram ram) {
        ramList.add(ram);
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Chassis getChassis() {
        return Chassis;
    }

    public void setChassis(Chassis Chassis) {
        this.Chassis = Chassis;
    }

    public Cooling getCooling() {
        return Cooling;
    }

    public void setCooling(Cooling Cooling) {
        this.Cooling = Cooling;
    }

    public Motherboard getMotherboard() {
        return Motherboard;
    }

    public void setMotherboard(Motherboard Motherboard) {
        this.Motherboard = Motherboard;
    }

    public Map<Gpu, Integer> getGpuCollection() {
        return gpuCollection;
    }

    public void setGpuCollection(Map<Gpu, Integer> gpuCollection) {
        this.gpuCollection = gpuCollection;
    }

    public void addToGpuCollection(Gpu gpu) {
        boolean checkGpuName = false;
        int newNum = 1;

        Entry entry = null;
        for (Entry ent : gpuCollection.entrySet()) {
            if (((Gpu) ent.getKey()).getModelName().equals(gpu.getModelName())) {
                newNum = Integer.valueOf(ent.getValue().toString()) + 1;
                entry = ent;
                checkGpuName = true;
            }
        }
        if (checkGpuName) {
            gpuCollection.entrySet().remove(entry);
            gpuCollection.put(gpu, newNum);
        } else {
            gpuCollection.put(gpu, 1);
        }
    }

    public HashMap<Gpu, Integer> gpuListToCollection(List<Gpu> gpuList) {
        HashMap<Gpu, Integer> gpuMap = new HashMap<>();
        HashMap<Gpu, Integer> switcheroo = new HashMap<>();

        // This netbeans suggested solutions somehow stops the problem of computer stopping being an entity.
        /*
        The dysfunctional alternative was: 
             ramList.stream().forEach((Ram o) -> {
         */
        gpuList.stream().forEach(new Consumer<Gpu>() {
            @Override
            public void accept(Gpu o) {
                if (gpuMap.entrySet().isEmpty()) {
                    gpuMap.put(o, 1);
                } //Avoiding out of bounds
                else if (!(gpuMap.entrySet()
                        .stream()
                        .anyMatch(j -> j.getKey()
                        .getModelName()
                        .equals(o.getModelName())))) {
                    gpuMap.put(o, 1);
                } else {
                    gpuMap.entrySet().stream().forEach(j -> {
                        if (j.getKey().getModelName().equals(o.getModelName())) {
                            switcheroo.put(j.getKey(), j.getValue() + 1);
                        }
                    });
                    switcheroo.entrySet().stream().forEach(j -> {
                        gpuMap.put(j.getKey(), j.getValue());
                    });
                }
            }
        });
        return gpuMap;
    }

    public Map<DiskDrive, Integer> getDiskDriveCollection() {
        return diskDriveCollection;
    }

    public void setDiskDriveCollection(Map<DiskDrive, Integer> diskDriveCollection) {
        this.diskDriveCollection = diskDriveCollection;
    }

    public HashMap<DiskDrive, Integer> diskDriveListToCollection(List<DiskDrive> diskDriveList) {
        HashMap<DiskDrive, Integer> diskDriveMap = new HashMap<>();
        HashMap<DiskDrive, Integer> switcheroo = new HashMap<>();

        diskDriveList.stream().forEach(new Consumer<DiskDrive>() {
            @Override
            public void accept(DiskDrive o) {
                if (diskDriveMap.entrySet().isEmpty()) {
                    diskDriveMap.put(o, 1);
                } //Avoiding out of bounds
                else if (!(diskDriveMap.entrySet()
                        .stream()
                        .anyMatch(j -> j.getKey()
                        .getModelName()
                        .equals(o.getModelName())))) {
                    diskDriveMap.put(o, 1);
                } else {
                    diskDriveMap.entrySet().stream().forEach(j -> {
                        if (j.getKey().getModelName().equals(o.getModelName())) {
                            switcheroo.put(j.getKey(), j.getValue() + 1);
                        }
                    });
                    switcheroo.entrySet().stream().forEach(j -> {
                        diskDriveMap.put(j.getKey(), j.getValue());
                    });
                }
            }
        });
        return diskDriveMap;
    }

    public void addToDiskDriveCollection(DiskDrive diskDrive) {

        boolean checkRamName = false;
        int newNum = 1;

        Entry entry = null;
        for (Entry ent : diskDriveCollection.entrySet()) {
            if (((DiskDrive) ent.getKey()).getModelName().equals(diskDrive.getModelName())) {
                newNum = Integer.valueOf(ent.getValue().toString()) + 1;
                entry = ent;
                checkRamName = true;
            }
        }
        if (checkRamName) {
            diskDriveCollection.entrySet().remove(entry);
            diskDriveCollection.put(diskDrive, newNum);
        } else {
            diskDriveCollection.put(diskDrive, 1);
        }
    }

    @Override
    public String toString() {
        return "Computer{" + "id=" + id + ", modelName=" + modelName + ", gpuCollection=" + gpuCollection + ", diskDriveCollection=" + diskDriveCollection + ", ramCollection=" + ramCollection + ", ramList=" + ramList + ", gpuList=" + gpuList + ", diskDriveList=" + diskDriveList + '}';
    }
}
