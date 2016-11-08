package home.maintenance.dao.common;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by vsoshyn on 26/10/2016.
 */
public abstract class AbstractDao<T> implements Dao<T> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public T merge(T entity) {
        return em.merge(entity);
    }

    @Override
    public void detach(T entity) {
        em.detach(entity);
    }

}
