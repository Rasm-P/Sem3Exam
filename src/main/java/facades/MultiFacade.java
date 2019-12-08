/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Lukas Bjornvad
 * @param <T>
 */
public class MultiFacade<T> extends AbstractFacade {

    private final EntityManagerFactory emf;
    private static MultiFacade<Computer> comp;
    private static MultiFacade<Motherboard> mother;

    public MultiFacade(Class<T> entityClass, EntityManagerFactory emf) {
        super(entityClass);
        this.emf = emf;
        if (!(entityClass.getSimpleName().equals("Computer"))) {
            comp = new MultiFacade(Computer.class, emf);
        }
        if (entityClass.getSimpleName().equals("Gpu") || entityClass.getSimpleName().equals("Cpu")) {
            mother = new MultiFacade(Motherboard.class, emf);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void remove(long entity) {
        String entityString = find(entity).getClass().getSimpleName().toUpperCase();
        if (!(entityString.equals("COMPUTER"))) {
            Entities.valueOf(entityString).uproot(find(entity));
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(ENTITY_CLASS, entity));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private enum Entities {
        CHASSIS {
            @Override
            public void uproot(Object obj) {
                for (Object com : comp.findAll()) {
                    if (((Computer) com).getChassis().getid().equals(((Chassis) obj).getid())) {
                        ((Computer) com).setChassis(null);
                        comp.edit(com);
                    }
                }
            }
        }, COOLING {
            @Override
            public void uproot(Object obj) {
                for (Object com : comp.findAll()) {
                    if (((Computer) com).getCooling().getid().equals(((Cooling) obj).getid())) {
                        ((Computer) com).setCooling(null);
                        comp.edit(com);
                    }
                }
            }
        }, CPU {
            @Override
            public void uproot(Object obj) {
                for (Object com : comp.findAll()) {
                    if (((Computer) com).getCpu().getid().equals(((Cpu) obj).getid())) {
                        ((Computer) com).setCpu(null);
                        comp.edit(com);
                    }
                }
                for (Object motherboard : mother.findAll()) {
                    List<Cpu> cpuList = null;
                    for (Cpu cpu : ((Motherboard) motherboard).getCompatibleCPUList()) {
                        if (!(cpu.getid().equals(((Cpu) obj).getid()))) {
                            cpuList.add(cpu);
                        }
                        ((Motherboard) motherboard).setCompatibleCPUList(cpuList);
                        mother.edit(motherboard);
                    }
                }
            }
        }, DISKDRIVE {
            @Override
            public void uproot(Object obj) {
                for (Object computer : comp.findAll()) {
                    HashMap<DiskDrive, Integer> switcheroo = new HashMap<>();
                    for (Entry entry : ((Computer) computer).getDiskDriveCollection().entrySet()) {
                        if (!((DiskDrive) entry.getKey()).getModelName().equals(((DiskDrive) obj).getModelName())) {
                            switcheroo.put(((DiskDrive) entry.getKey()), Integer.valueOf(entry.getValue().toString()));
                        }
                    }
                    ((Computer) computer).setDiskDriveCollection(switcheroo);
                    comp.edit(computer);
                }
            }
        }, GPU {
            @Override
            public void uproot(Object obj) {
                for (Object computer : comp.findAll()) {
                    HashMap<Gpu, Integer> switcheroo = new HashMap<>();
                    for (Entry entry : ((Computer) computer).getGpuCollection().entrySet()) {
                        if (!((Gpu) entry.getKey()).getModelName().equals(((Gpu) obj).getModelName())) {
                            switcheroo.put(((Gpu) entry.getKey()), Integer.valueOf(entry.getValue().toString()));
                        }
                    }
                    ((Computer) computer).setGpuCollection(switcheroo);
                    comp.edit(computer);
                }
                for (Object motherboard : mother.findAll()) {
                    List<Gpu> gpuList = null;
                    for (Gpu gpu : ((Motherboard) motherboard).getCompatibleGPUList()) {
                        if (!(gpu.getid().equals(((Gpu) obj).getid()))) {
                            gpuList.add(gpu);
                        }
                    }
                    ((Motherboard) motherboard).setCompatibleGPUList(gpuList);
                    mother.edit(motherboard);
                }
            }
        }, MOTHERBOARD {
            @Override
            public void uproot(Object obj) {
                for (Object com : comp.findAll()) {
                    if (((Computer) com).getMotherboard().getid().equals(((Motherboard) obj).getid())) {
                        ((Computer) com).setMotherboard(null);
                        comp.edit(com);
                    }
                }
            }
        }, PSU {
            @Override
            public void uproot(Object obj) {
                for (Object com : comp.findAll()) {
                    if (((Computer) com).getPsu().getid().equals(((Psu) obj).getid())) {
                        ((Computer) com).setPsu(null);
                        comp.edit(com);
                    }
                }
            }
        }, RAM {
            @Override
            public void uproot(Object obj) {
                HashMap<Ram, Integer> switcheroo = new HashMap<>();
                Entry ent = null;
                if (comp.findAll().size() > 0) {
                    for (Object computer : comp.findAll()) {
                        for (Entry entry : ((Computer) computer).getRamCollection().entrySet()) {
                            if (!((Ram) entry.getKey()).getModelName().equals(((Ram) obj).getModelName())) {
                                switcheroo.put(((Ram) entry.getKey()), Integer.valueOf(entry.getValue().toString()));
                            }
                        }
                        ((Computer) computer).setRamCollection(switcheroo);
                        comp.edit(((Computer) computer));

                    }
                }

            }
        };

        public abstract void uproot(Object obj);
    }
}
