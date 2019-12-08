package facades;

import dto.ComputerDTO;
import entities.Chassis;
import entities.Computer;
import entities.Cooling;
import entities.Cpu;
import entities.DiskDrive;
import entities.Gpu;
import entities.Motherboard;
import entities.Psu;
import entities.Ram;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Lukas Bjornvad
 */
//@Disabled
public class MultiFacadeTest {

    MultiFacade<Chassis> chassisFacade;
    MultiFacade<Gpu> gpuFacade;
    MultiFacade<Cooling> coolingFacade;
    MultiFacade<Cpu> cpuFacade;
    MultiFacade<DiskDrive> ddFacade;
    MultiFacade<Motherboard> mbFacade;
    MultiFacade<Psu> psuFacade;
    MultiFacade<Ram> ramFacade;
    MultiFacade<Computer> compFacade;
    private static EntityManagerFactory emf;

    public MultiFacadeTest() {
        gpuFacade = new MultiFacade(Gpu.class, emf);
        chassisFacade = new MultiFacade(Chassis.class, emf);
        coolingFacade = new MultiFacade(Cooling.class, emf);
        cpuFacade = new MultiFacade(Cpu.class, emf);
        ddFacade = new MultiFacade(DiskDrive.class, emf);
        mbFacade = new MultiFacade(Motherboard.class, emf);
        psuFacade = new MultiFacade(Psu.class, emf);
        ramFacade = new MultiFacade(Ram.class, emf);
        compFacade = new MultiFacade(Computer.class, emf);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
    }

    @AfterAll
    public static void tearDownClass() {
    }
    Gpu gpu = new Gpu("modelname", "manu", 400.0, 5.0, 50, 45.0, 60.0, 100, "descr");
    Chassis chassis = new Chassis("Chassisname", "ChassisManu", 20.0, 5.0, 150.0, 50.0, 30.0, "Descrip");
    Cooling cooling = new Cooling("Coolname", "coolmanu", 500.0, 5.0, "Fans", "Water");
    Cpu cpu = new Cpu("proccessingname", "process manufact", 12, 45, 50.0, 75, "Pretty fast", 500.0, 5.0);
    DiskDrive dd = new DiskDrive("DDname", "DDManu", 444.4, 12, "ssd", "DDdesc", 2.5);
    Motherboard mb = new Motherboard("motherbname", "MotherMan", 50.0, 45, 800, 5, "KindaWeird", "WorstOption", 98, 20, 7.8);
    Psu psu = new Psu("Psuname", "PsuManu", 50.0, 600, "Descrpsu", 3.0);
    Ram ram = new Ram("RamName", "RamManu", 150.0, 12, 8.0, "Ramdesc", 5.5);

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Computer.deleteAll").executeUpdate();
            em.createNamedQuery("Gpu.deleteAll").executeUpdate();
            em.createNamedQuery("Chassis.deleteAll").executeUpdate();
            em.createNamedQuery("Cooling.deleteAll").executeUpdate();
            em.createNamedQuery("Cpu.deleteAll").executeUpdate();
            em.createNamedQuery("DiskDrive.deleteAll").executeUpdate();
            em.createNamedQuery("Motherboard.deleteAll").executeUpdate();
            em.createNamedQuery("Psu.deleteAll").executeUpdate();
            em.createNamedQuery("Ram.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        chassisFacade.create(chassis);
        gpuFacade.create(gpu);
        coolingFacade.create(cooling);
        cpuFacade.create(cpu);
        ddFacade.create(dd);
        mbFacade.create(mb);
        psuFacade.create(psu);
        ramFacade.create(ram);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class ChassisFacade. Chassis(String
     * modelName, String manufacturer, Double price, Integer height, Integer
     * width, Integer depth, Double rating, String description) Gpu(String
     * modelName, String manufacturer, Double price, Double rating, Integer
     * memory, Double baseClockSpeed, Double boostClockSpeed, Integer
     * powerConsumption, String description)
     */
    @Test
    public void testGetGpu() {
        Gpu act = (Gpu) gpuFacade.find(gpu.getid());
        assertTrue(Objects.equals(gpu.getid(), act.getid()));
        assertEquals(gpu.getModelName(), act.getModelName());
    }

    @Test
    public void testEditGpu() {
        Gpu ed = new Gpu("editname", "editmanu", 430.0, 3.0, 20, 12.0, 70.0, 500, "editeddescr");
        ed.setid(gpu.getid());
        gpuFacade.edit(ed);
        Gpu act = (Gpu) gpuFacade.find(gpu.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddGpu() {
        Gpu newg = new Gpu("newname", "newmanu", 430.0, 3.0, 20, 12.0, 70.0, 500, "neweddescr");
        gpuFacade.create(newg);
        assertEquals(2, gpuFacade.findAll().size());
    }

    @Test
    public void testRemoveGpu() {
        int listSize = gpuFacade.findAll().size();
        gpuFacade.remove(gpu.getid());
        assertEquals(listSize - 1, gpuFacade.findAll().size());
    }

    @Test
    public void testCreateChassis() {
        Chassis act = (Chassis) chassisFacade.find(chassis.getid());
        assertTrue(Objects.equals(chassis.getid(), act.getid()));
        assertEquals(chassis.getModelName(), act.getModelName());
    }

    @Test
    public void testEditChassis() {
        Chassis ed = new Chassis("editname", "editmanu", 30.0, 2.0, 190.0, 20.0, 10.0, "EditDescrip");
        ed.setid(chassis.getid());
        chassisFacade.edit(ed);
        Chassis act = (Chassis) chassisFacade.find(chassis.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddChassis() {
        Chassis newg = new Chassis("newname", "newmanu", 65.0, 2.0, 890.0, 32.0, 87.0, "NewDescrip");
        chassisFacade.create(newg);
        assertEquals(2, chassisFacade.findAll().size());
    }

    @Test
    public void testRemoveChassis() {
        int listSize = chassisFacade.findAll().size();
        chassisFacade.remove(chassis.getid());
        assertEquals(listSize - 1, chassisFacade.findAll().size());
    }

    @Test
    public void testCreateCooling() {
        Cooling act = (Cooling) coolingFacade.find(cooling.getid());
        assertTrue(Objects.equals(cooling.getid(), act.getid()));
        assertEquals(cooling.getModelName(), act.getModelName());
    }

    @Test
    public void testEditCooling() {
        Cooling ed = new Cooling("editname", "editmanu", 500.0, 5.0, "Fans", "air");
        ed.setid(cooling.getid());
        coolingFacade.edit(ed);
        Cooling act = (Cooling) coolingFacade.find(cooling.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddCooling() {
        Cpu newg = new Cpu("newname", "newmanu", 16, 89, 20.0, 45, "Pretty fast", 200.0, 3.0);
        cpuFacade.create(newg);
        assertEquals(2, cpuFacade.findAll().size());
    }

    @Test
    public void testRemoveCooling() {
        int listSize = coolingFacade.findAll().size();
        coolingFacade.remove(cooling.getid());
        assertEquals(listSize - 1, coolingFacade.findAll().size());
    }

    @Test
    public void testCreateCpu() {
        Cpu act = (Cpu) cpuFacade.find(cpu.getid());
        assertTrue(Objects.equals(cpu.getid(), act.getid()));
        assertEquals(cpu.getModelName(), act.getModelName());
    }

    @Test
    public void testEditCpu() {
        Cpu ed = new Cpu("editname", "editmanu", 16, 65, 54.0, 77, "Pretty slow", 200.0, 3.3);
        ed.setid(cpu.getid());
        cpuFacade.edit(ed);
        Cpu act = (Cpu) cpuFacade.find(cpu.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddCpu() {
        Cpu newg = new Cpu("newname", "newmanu", 12, 45, 50.0, 75, "Pretty goddamn fast", 500.0, 5.0);
        cpuFacade.create(newg);
        assertEquals(2, cpuFacade.findAll().size());
    }

    @Test
    public void testRemoveCpu() {
        int listSize = cpuFacade.findAll().size();
        cpuFacade.remove(cpu.getid());
        assertEquals(listSize - 1, cpuFacade.findAll().size());
    }

    @Test
    public void testCreateDiskDrive() {
        DiskDrive act = (DiskDrive) ddFacade.find(dd.getid());
        assertTrue(Objects.equals(dd.getid(), act.getid()));
        assertEquals(dd.getModelName(), act.getModelName());
    }

    @Test
    public void testEditDiskDrive() {
        DiskDrive ed = new DiskDrive("editname", "editmanu", 444.4, 12, "hdd", "editDDdesc", 2.3);
        ed.setid(dd.getid());
        ddFacade.edit(ed);
        DiskDrive act = (DiskDrive) ddFacade.find(dd.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddDiskDrive() {
        DiskDrive newg = new DiskDrive("newname", "newmanu", 456.4, 32, "hdd", "newDDdesc", 4.3);
        ddFacade.create(newg);
        assertEquals(2, ddFacade.findAll().size());
    }

    @Test
    public void testRemoveDiskDrive() {
        int listSize = ddFacade.findAll().size();
        ddFacade.remove(dd.getid());
        assertEquals(listSize - 1, ddFacade.findAll().size());
    }

    @Test
    public void testCreateMotherboard() {
        Motherboard act = (Motherboard) mbFacade.find(mb.getid());
        assertTrue(Objects.equals(mb.getid(), act.getid()));
        assertEquals(mb.getModelName(), act.getModelName());
    }

    @Test
    public void testEditMotherboard() {

    }

    @Test
    public void testAddMotherboard() {
        Motherboard newg = new Motherboard("newname", "newmanu", 50.0, 45, 800, 5, "Boring", "Kaplowski", 98, 20, 7.8);
        mbFacade.create(newg);
        assertEquals(2, mbFacade.findAll().size());
    }

    @Test
    public void testRemoveMotherboard() {
        int listSize = mbFacade.findAll().size();
        mbFacade.remove(mb.getid());
        assertEquals(listSize - 1, mbFacade.findAll().size());
    }

    @Test
    public void testCreatePsu() {
        Psu act = (Psu) psuFacade.find(psu.getid());
        assertTrue(Objects.equals(psu.getid(), act.getid()));
        assertEquals(psu.getModelName(), act.getModelName());
    }

    @Test
    public void testEditPsu() {
        Psu ed = new Psu("editname", "editmanu", 50.0, 600, "editDescrpsu", 3.0);
        ed.setid(psu.getid());
        psuFacade.edit(ed);
        Psu act = (Psu) psuFacade.find(psu.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddPsu() {
        Psu newg = new Psu("newname", "newmanu", 50.0, 600, "newDescrpsu", 3.0);
        psuFacade.create(newg);
        assertEquals(2, psuFacade.findAll().size());
    }

    @Test
    public void testRemovePsu() {
        int listSize = psuFacade.findAll().size();
        psuFacade.remove(psu.getid());
        assertEquals(listSize - 1, psuFacade.findAll().size());
    }

    @Test
    public void testCreateRam() {
        Ram act = (Ram) ramFacade.find(ram.getid());
        assertTrue(Objects.equals(ram.getid(), act.getid()));
        assertEquals(ram.getModelName(), act.getModelName());
    }

    @Test
    public void testEditRam() {
        Ram ed = new Ram("editname", "editmanu", 150.0, 12, 8.0, "editRamdesc", 5.5);
        ed.setid(ram.getid());
        ramFacade.edit(ed);
        Ram act = (Ram) ramFacade.find(ram.getid());
        assertEquals(ed.getModelName(), act.getModelName());
    }

    @Test
    public void testAddRam() {
        Ram newg = new Ram("newname", "newmanu", 150.0, 12, 8.0, "NewRamdesc", 5.5);
        ramFacade.create(newg);
        assertEquals(2, ramFacade.findAll().size());
    }

    @Test
    public void testRemoveRam() {
        int listSize = ramFacade.findAll().size();
        ramFacade.remove(ram.getid());
        assertEquals(listSize - 1, ramFacade.findAll().size());
    }

    @Test
    public void testAddComp() {
        int size = compFacade.findAll().size();
        compFacade.create(new Computer("Keklord Prime", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu));
        assertEquals(size + 1, compFacade.findAll().size());
    }

    @Test
    public void testEditCompName() {
        Computer ed = new Computer("Keklord Prime", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        ed.setModelName("Razzakield Master Mega Mode Extreme of Doom");
        compFacade.edit(ed);
        assertEquals("Razzakield Master Mega Mode Extreme of Doom", ed.getModelName());
    }

    @Test
    public void testEditGpuCollection() {
        Computer ed = new Computer("Keklord Prime", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        ed.addToGpuCollection(gpu);
        compFacade.create(ed);
        compFacade.edit(ed);
        assertEquals(1, ((Computer) compFacade.find(ed.getid())).getGpuCollection().size());
        assertTrue(compFacade.findAll().stream().anyMatch(o -> ((Computer) o).getGpuCollection().size() == 1));
    }

    @Test
    @Disabled
    public void testEditRamCollection() {
        Ram ramInstance = new Ram("cake", "ASC", 1.1, 123, 1.1, "asdf", 2.2);
        ramFacade.create(ramInstance);

        Computer ed = new Computer("Keklord Prime", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        ed.addToRamCollection(ram);
        ed.addToRamCollection(ram);
        ed.addToRamCollection(ram);
        ed.addToRamCollection(ram);

        assertEquals(4, ed.getRamCollection()
                .entrySet()
                .stream()
                .findFirst()
                .get()
                .getValue()
                .intValue());
        compFacade.create(ed);

        // comp.edit(ed);
        assertEquals(1, ((Computer) compFacade.find(ed.getid()))
                .getRamCollection().size());
        assertTrue(compFacade.findAll()
                .stream()
                .anyMatch(o -> ((Computer) o)
                .getRamCollection()
                .size() == 1));

        assertEquals(4, ((Computer) compFacade.find(ed.getid()))
                .getRamCollection()
                .entrySet()
                .stream()
                .findFirst()
                .get()
                .getValue()
                .intValue());

        compFacade.findAll()
                .stream()
                .forEach(o
                        -> assertTrue(((Computer) o)
                        .getRamCollection()
                        .entrySet()
                        .stream()
                        .anyMatch(j -> j.getValue() == 4))
                );

        ed.addToRamCollection(ramInstance);
        ed.addToRamCollection(ramInstance);
        ed.addToRamCollection(ramInstance);
        compFacade.edit(ed);
        assertTrue(ed.getRamCollection()
                .entrySet()
                .stream()
                .anyMatch(o -> o.getValue().equals(3)));

        assertTrue(ed.getRamCollection()
                .entrySet()
                .stream()
                .anyMatch(o -> o.getValue().equals(4)));

        compFacade.findAll()
                .stream()
                .forEach(o
                        -> assertTrue(((Computer) o)
                        .getRamCollection()
                        .entrySet()
                        .stream()
                        .anyMatch(j -> j.getValue() == 4))
                );

        compFacade.findAll()
                .stream()
                .forEach(o
                        -> assertTrue(((Computer) o)
                        .getRamCollection()
                        .entrySet()
                        .stream()
                        .anyMatch(j -> j.getValue() == 3))
                );

        ramFacade.remove(ram.getid());

        assertEquals(1, ((Computer) compFacade.find(ed.getid())).getRamCollection().size());
    }

    @Disabled
    @Test
    public void computerListRamDeletionTest() {
        Computer com = new Computer("Razzakield", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        Ram ramInstance = new Ram("cake", "ASC", 1.1, 123, 1.1, "asdf", 2.2);
        ramFacade.create(ramInstance);

        for (int i = 0; i < 4; i++) {
            com.addToRamCollection(ram);
        }

        for (int i = 0; i < 7; i++) {
            com.addToRamCollection(ramInstance);
        }

        compFacade.create(com);

        //Are there any rm Rams?
        compFacade.findAll()
                .stream()
                .forEach(computer -> {
                    assertTrue(((Computer) computer).getRamCollection()
                            .entrySet()
                            .stream()
                            .anyMatch(j -> j.getKey().getModelName().equals(ram.getModelName())
                            ));
                });

        //How many 'unique' rams are there?
        assertEquals(2, ((Computer) (compFacade.find(com.getid()))).getRamCollection().size());

        //Is there a ram entry which has the value 4? (rm)
        assertTrue(((Computer) (compFacade.find(com.getid()))).
                getRamCollection()
                .entrySet()
                .stream()
                .anyMatch(o -> (o.getValue() == 4)));

        //Is there a ram entry which has the value 7? (ramInstance)
        assertTrue(((Computer) (compFacade.find(com.getid()))).
                getRamCollection()
                .entrySet()
                .stream()
                .anyMatch(o -> (o.getValue() == 7)));

        ramFacade.remove(ram.getid());

        assertTrue(!(((Computer) compFacade.find(com.getid())).getRamCollection()
                .entrySet()
                .stream()
                .anyMatch(j -> j.getKey().getModelName().equals(ram.getModelName())
                )));

        compFacade.edit(com);

        assertEquals(true, compFacade.findAll()
                .stream()
                .anyMatch(o -> ((Computer) o)
                .getRamList()
                .get(0)
                .getModelName()
                .equals(ram.getModelName())));
    }

    @Test
    public void testComputerRamCollectionSize() {
        Computer com = new Computer("Razzakield", new HashMap<Gpu, Integer>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        com.addToRamCollection(ram);
        compFacade.create(com);
        assertEquals(1, ((Computer) compFacade.find(com.getid()))
                .getRamCollection()
                .entrySet()
                .stream()
                .filter(o -> o.getKey()
                .getModelName()
                .equals(ram.getModelName()))
                .findFirst()
                .get()
                .getValue()
                .intValue());

    }

    @Test
    public void computerDTOListSize() {
        Computer com = new Computer("Razzakield", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        compFacade.create(com);
        ComputerDTO comDTO = new ComputerDTO(com);
        assertEquals(4, comDTO.getRamList().size());

        Ram ramInstance = new Ram("cake", "ASC", 1.1, 123, 1.1, "asdf", 2.2);
        ramFacade.create(ramInstance);

        com.addToRamCollection(ramInstance);
        com.addToRamCollection(ramInstance);
        com.addToRamCollection(ramInstance);
        compFacade.edit(com);
        ComputerDTO comDTO2 = new ComputerDTO(com);
        assertEquals(7, comDTO2.getRamList().size());

    }

    @Disabled
    @Test
    public void computerFind() {
        Computer com = new Computer("Razzakield", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        compFacade.create(com);

        Computer comFound = ((Computer) (compFacade.find(com.getid())));
    }

    //@Disabled
    @Test
    public void computerTestEditConsole() {
        System.out.println("\nTest Edit and Get By ID");
        Computer com = new Computer("BI Expresso 250ML", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToRamCollection(ram);
        com.addToDiskDriveCollection(dd);
        System.out.println("#1 Init: " + com);
        compFacade.create(com);
        System.out.println("#1.5 Created");

        Computer comFound = (Computer) compFacade.find(com.getid());
        System.out.println("\n#2 Found (with these Components):");
        System.out.println("#2.1 RAM Count: " + comFound.getRamCollection().entrySet().stream().findFirst().get().getValue());
        for (Entry<Ram, Integer> entry : comFound.getRamCollection().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.println("#2.1." + i + " RAM: " + entry.getKey());
            }
        }
        System.out.println("#2.2 DiskDrive Count: " + comFound.getDiskDriveCollection().entrySet().stream().findFirst().get().getValue());
        for (Entry<DiskDrive, Integer> entry : comFound.getDiskDriveCollection().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.println("#2.2." + i + " Disk Drive: " + entry.getKey());
            }
        }

        Computer comEdit = new Computer("Bold Icepresso 25XL", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        comEdit.setid(comFound.getid());
        comEdit.addToRamCollection(ram);
        comEdit.addToRamCollection(ram);
        comEdit.addToDiskDriveCollection(dd);
        comEdit.addToDiskDriveCollection(dd);
        comEdit.addToDiskDriveCollection(dd);
        System.out.println("\n#3 Init New Computer With Same ID: " + comEdit);

        compFacade.edit(comEdit);
        System.out.println("3.5 Edited");

        comFound = (Computer) compFacade.find(com.getid());
        System.out.println("\n#4 Found Edited (with these Components):");
        System.out.println("#4.1 RAM Count: " + comFound.getRamCollection().entrySet().stream().findFirst().get().getValue());
        for (Entry<Ram, Integer> entry : comFound.getRamCollection().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.println("#4.1." + i + " RAM: " + entry.getKey());
            }
        }
        System.out.println("#4.2 Disk Drive Count: " + comFound.getDiskDriveCollection().entrySet().stream().findFirst().get().getValue());
        for (Entry<DiskDrive, Integer> entry : comFound.getDiskDriveCollection().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.println("#4.2." + i + " Disk Drive: " + entry.getKey());
            }
        }
        System.out.println("\n");
    }

    //@Disabled
    @Test
    public void computerTestEdit() {
        System.out.println("\nAsserting:");
        for (int i = 1; i < 10; i++) {
            Computer com = new Computer("Razzakield-" + i, new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
            com.addToRamCollection(ram);
            com.addToRamCollection(ram);
            com.addToRamCollection(ram);
            com.addToRamCollection(ram);
            com.addToDiskDriveCollection(dd);
            com.addToGpuCollection(gpu);
            com.addToGpuCollection(gpu);
            compFacade.create(com);

            Computer comFound = (Computer) compFacade.find(com.getid());
            assertEquals(4, comFound.getRamCollection().entrySet().stream().findFirst().get().getValue().intValue());
            assertEquals(1, comFound.getDiskDriveCollection().entrySet().stream().findFirst().get().getValue().intValue());
            assertEquals(2, comFound.getGpuCollection().entrySet().stream().findFirst().get().getValue().intValue());

            Computer comEdit = new Computer("Bold Icepresso 25XL-" + i, new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
            comEdit.setid(comFound.getid());
            comEdit.addToRamCollection(ram);
            comEdit.addToRamCollection(ram);
            comEdit.addToDiskDriveCollection(dd);
            comEdit.addToDiskDriveCollection(dd);
            comEdit.addToDiskDriveCollection(dd);
            comEdit.addToGpuCollection(gpu);

            compFacade.edit(comEdit);

            comFound = (Computer) compFacade.find(com.getid());
            assertEquals(2, comFound.getRamCollection().entrySet().stream().findFirst().get().getValue().intValue());
            assertEquals(3, comFound.getDiskDriveCollection().entrySet().stream().findFirst().get().getValue().intValue());
            assertEquals(1, comFound.getGpuCollection().entrySet().stream().findFirst().get().getValue().intValue());

            System.out.println("SUCCESS COUNT: " + i + "!");
        }
        System.out.println("\n");
    }

    @Test
    public void computerTestEditGetAllConsole() {
        System.out.println("\nTest Edit and Get All");
        Computer com = new Computer("BI Expresso 250ML", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        com.addToRamCollection(ram);
        com.addToGpuCollection(gpu);
        Computer com2 = new Computer("Motown Mastermind", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
        com2.addToDiskDriveCollection(dd);
        com2.addToDiskDriveCollection(dd);
        System.out.println("#1.1 Init: " + com);
        System.out.println("#1.2 Init: " + com2);
        compFacade.create(com);
        compFacade.create(com2);
        System.out.println("#1.5 Created");

        List<Computer> comsFound = (List<Computer>) compFacade.findAll();
        System.out.println("\n#2 Computers Found");
        for (Computer computer : comsFound) {
            System.out.println("#2." + computer.getid() + " (with these components)");
            Set<Entry<Ram, Integer>> ramSet = computer.getRamCollection().entrySet();
            if (!ramSet.isEmpty()) {
                System.out.println("RAM Count: " + ramSet.stream().findFirst().get().getValue());
            }
            Set<Entry<Gpu, Integer>> gpuSet = computer.getGpuCollection().entrySet();
            if (!gpuSet.isEmpty()) {
                System.out.println("GPU Count: " + gpuSet.stream().findFirst().get().getValue());
            }
            Set<Entry<DiskDrive, Integer>> ddSet = computer.getDiskDriveCollection().entrySet();
            if (!ddSet.isEmpty()) {
                System.out.println("Disk Drive Count: " + ddSet.stream().findFirst().get().getValue());
            }
        }

        for (Computer computer : comsFound) {
            Computer comEdit = new Computer("Edited PC", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
            comEdit.setid(computer.getid());
            comEdit.addToRamCollection(ram);
            comEdit.addToRamCollection(ram);
            comEdit.addToDiskDriveCollection(dd);
            System.out.println("\n#3." + computer.getid() + " Init New Computer With Same ID: " + comEdit);

            compFacade.edit(comEdit);
        }
        System.out.println("3.5 Edited");

        comsFound = (List<Computer>) compFacade.findAll();
        System.out.println("\n#4 Computers Found");
        for (Computer computer : comsFound) {
            System.out.println("#4." + computer.getid() + " (with these components)");
            Set<Entry<Ram, Integer>> ramSet = computer.getRamCollection().entrySet();
            if (!ramSet.isEmpty()) {
                System.out.println("RAM Count: " + ramSet.stream().findFirst().get().getValue());
            }
            Set<Entry<Gpu, Integer>> gpuSet = computer.getGpuCollection().entrySet();
            if (!gpuSet.isEmpty()) {
                System.out.println("GPU Count: " + gpuSet.stream().findFirst().get().getValue());
            }
            Set<Entry<DiskDrive, Integer>> ddSet = computer.getDiskDriveCollection().entrySet();
            if (!ddSet.isEmpty()) {
                System.out.println("Disk Drive Count: " + ddSet.stream().findFirst().get().getValue());
            }
        }
        System.out.println("\n");
    }

    //@Disabled
    @Test
    public void computerTestEdit2() {
        System.out.println("\nAsserting:");
        for (int i = 1; i < 10; i++) {
            Computer com = new Computer("BI Expresso 250ML", new HashMap<>(), new HashMap<>(), new HashMap<>(), cpu, chassis, cooling, mb, psu);
            compFacade.create(com);
            com.addToRamCollection(ram);
            com.addToGpuCollection(gpu);
            com.addToDiskDriveCollection(dd);
            compFacade.edit(com);

            Computer comFound = (Computer) compFacade.find(com.getid());

            comFound.addToDiskDriveCollection(dd);
            comFound.addToDiskDriveCollection(dd);

            compFacade.edit(comFound);

            Computer comFound2 = (Computer) compFacade.find(com.getid());
            assertEquals(1, comFound2.getRamCollection().entrySet().stream().findFirst().get().getValue().intValue());
            assertEquals(3, comFound2.getDiskDriveCollection().entrySet().stream().findFirst().get().getValue().intValue());
            assertEquals(1, comFound2.getGpuCollection().entrySet().stream().findFirst().get().getValue().intValue());

            System.out.println("SUCCESS COUNT: " + i + "!");
        }
        System.out.println("\n");
    }
}
