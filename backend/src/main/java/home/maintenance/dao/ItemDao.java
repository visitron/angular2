package home.maintenance.dao;

import home.maintenance.dao.common.Dao;
import home.maintenance.model.Item;

import java.util.List;

/**
 * Created by Buibi on 29.10.2016.
 */
public interface ItemDao extends Dao<Item> {
    List<Item> getAll();
}
