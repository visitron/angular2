package home.maintenance.dao;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

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


}
