package home.maintenance.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by vsoshyn on 26/10/2016.
 */
public abstract class GenericDao<T> extends HibernateDaoSupport {

    public T getById(Class<T> clazz, long id) {
        return getHibernateTemplate().get(clazz, id);
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public T save(T entity) {
        return (T) getHibernateTemplate().save(entity);
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    public void detach(T entity) {
        getSessionFactory().getCurrentSession().detach(entity);
    }

    public abstract List<T> getAll();

    protected List<T> getAll(Class<T> clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

}
