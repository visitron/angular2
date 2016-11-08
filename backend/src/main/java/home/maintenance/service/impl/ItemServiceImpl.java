package home.maintenance.service.impl;

import home.maintenance.dao.ItemDao;
import home.maintenance.model.Item;
import home.maintenance.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vsoshyn on 07/11/2016.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> findAll() {
        return itemDao.getAll();
    }

    @Override
    @Transactional
    public void save(Item item) {
        itemDao.persist(item);
    }

    @Override
    public void delete(Item item) {
        itemDao.remove(item);
    }
}
