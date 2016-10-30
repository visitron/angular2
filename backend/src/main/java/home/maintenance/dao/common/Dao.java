package home.maintenance.dao.common;

/**
 * Created by Buibi on 29.10.2016.
 */
public interface Dao<T> {
    void remove(T entity);
    void persist(T entity);
    T merge(T entity);
    void detach(T entity);
}
