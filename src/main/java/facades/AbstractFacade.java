package facades;

import entities.Computer;
import entities.Ram;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Henning
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    protected final Class<T> ENTITY_CLASS;

    public AbstractFacade(Class<T> entityClass) {
        this.ENTITY_CLASS = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void edit(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
//            if (entity.getClass().getSimpleName().toUpperCase().equals("COMPUTER")) {
//                Map<Ram, Integer> ramMap = ((Computer) entity).getRamCollection(); // maybe check if null
//                Entry ent = null;
//                for (Map.Entry<Ram, Integer> entry : ramMap.entrySet()) {
//                    Ram ram = entry.getKey();
//                    if (!em.contains(ram)) {  // type is transient
//                        ram = em.merge(ram); // or load the type
//                        ramMap.entrySet().add(
//                        // do more?
//                        
//                        
//                    }
//                }
//                ((Computer) entity).setRamCollection(ramMap); // update the reference
//            }

            em.merge(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remove(long entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(ENTITY_CLASS, entity));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public T find(long id) {
        Object object = null;
        EntityManager em = getEntityManager();
        try {
            object = em.find(ENTITY_CLASS, id);
//            em.refresh(object);
        } finally {
            em.close();
        }
        return (T) object;
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        List<T> list = null;
        try {
            em.getTransaction().begin();
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(ENTITY_CLASS));
            list = getEntityManager().createQuery(cq).getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return list;
    }
}
