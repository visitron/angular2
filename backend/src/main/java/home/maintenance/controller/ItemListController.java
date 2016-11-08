package home.maintenance.controller;

import home.maintenance.model.Item;
import home.maintenance.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@CrossOrigin
@RequestMapping("/item")
public class ItemListController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/getAll", produces = "application/json")
    @ResponseBody public List<Item> getItems() {
        return itemService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public boolean saveItem(@RequestBody Item item) {
        System.out.println("Item is got: " + item);
        itemService.save(item);
        return true;
    }

    @RequestMapping(produces = "text/plain")
    @ResponseBody public String index() {
        return "Hello";
    }
}
