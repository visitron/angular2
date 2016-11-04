package home.maintenance.controller;

import home.maintenance.dao.ItemDao;
import home.maintenance.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@CrossOrigin
@RequestMapping("/item")
public class ItemListController {

    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "/getAll", produces = "application/json")
    @ResponseBody public List<Item> getItems() {
        return itemDao.getAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody public boolean saveItem(Item item) {
        itemDao.persist(item);
        System.out.println(item + " was persisted");
        return true;
    }

    @RequestMapping(produces = "text/plain")
    @ResponseBody public String index() {
        return "Hello";
    }
}
