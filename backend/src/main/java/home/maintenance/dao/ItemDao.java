package home.maintenance.dao;

import home.maintenance.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vsoshyn on 28/10/2016.
 */
//@Repository
//@Transactional
public class ItemDao extends GenericDao<Item> {
    @Override
    public List<Item> getAll() {
        return getAll(Item.class);
    }
}
