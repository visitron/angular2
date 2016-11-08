package home.maintenance.service;

import home.maintenance.model.Item;

import java.util.List;

/**
 * Created by vsoshyn on 07/11/2016.
 */
public interface ItemService {
    List<Item> findAll();
    void save(Item item);
    void delete(Item item);
}
