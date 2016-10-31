package home.maintenance.dao.impl;

import home.maintenance.dao.ItemDao;
import home.maintenance.dao.common.AbstractDao;
import home.maintenance.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import java.util.List;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Repository
@Transactional
@NamedNativeQueries({@NamedNativeQuery(name = "Item.getAll", query = "SELECT * FROM ITEM")})
public class ItemDaoImpl extends AbstractDao<Item> implements ItemDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getAll() {
        return em.createNativeQuery("SELECT * FROM ITEM", Item.class).getResultList();
//        return em.createNamedQuery("Item.getAll", Item.class).getResultList();
    }

}
