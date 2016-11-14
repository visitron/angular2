package home.maintenance.dao;

import home.maintenance.model.EInfo;
import home.maintenance.model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Buibi on 09.11.2016.
 */
public class AbstractDaoTest {

    protected Item createItem(String prefix0, int prefix1) {
        Item item = new Item();
        item.setName("Name " + prefix0);
        item.setDescription("Description " + prefix0);
        item.setLifecycle(150 + prefix1);
        item.setMaintenanceDate(new Date());
        List<EInfo> infos = new ArrayList<>();
        infos.add(EInfo.INFO);
        infos.add(EInfo.CART);
        infos.add(EInfo.MONEY);
        infos.add(EInfo.PHONE);
        item.setInfo(infos);
        return item;
    }

}