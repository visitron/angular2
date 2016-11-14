package home.maintenance.dao;

import home.maintenance.config.PersistenceConfigTest;
import home.maintenance.model.AdvancedItem;
import home.maintenance.model.EInfo;
import home.maintenance.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Buibi on 09.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("h2-ci")
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
public class ItemDaoTest extends AbstractDaoTest {

    @Autowired
    private ItemDao dao;

    //todo SubSelect || SecondTable ??

    @Test
    public void persist() throws Exception {
        Item item0 = createItem("0", 0);
        Item item1 = createItem("1", 10);
        Item item2 = createItem("2", 50);
        Item item3 = createItem("3", 100);

        dao.persist(item0);
        dao.persist(item1);
        dao.persist(item2);
        dao.persist(item3);

        item0.setName("Changed 0");
        item1.getInfo().remove(EInfo.INFO);
        dao.remove(item2);

        dao.flush();

        List<Item> items = dao.getAll();

        assertEquals(3, items.size());
    }

    @Test
    public void getAll() throws Exception {

        Item item0 = new Item();
        item0.setName("Some item");
        item0.setDescription("Some description");
        item0.setLifecycle(500);
        item0.setMaintenanceDate(new Date());

        List<EInfo> infos_ = new ArrayList<>();
        infos_.add(EInfo.CART);
        infos_.add(EInfo.INFO);
        infos_.add(EInfo.PHOTO);
        item0.setInfo(infos_);

        dao.persist(item0);
        dao.flush();

        item0.getInfo().remove(0);
        item0.getInfo().remove(0);

        dao.getAll().forEach(item -> {
            System.out.println(item);
            System.out.print("; Info = ");
            Optional<List<EInfo>> infos = Optional.of(item.getInfo());
            infos.ifPresent(info -> System.out.print(info + ", "));

            if (item instanceof AdvancedItem) {
                AdvancedItem tmp = (AdvancedItem) item;
                Optional<AdvancedItem.Specialist> optional = Optional.of(tmp.getSpecialist());
                optional.ifPresent(specialist -> {
                    System.out.println("\t" + specialist.getCompany());
                    System.out.println("\t" + specialist.getPhone());
                    System.out.println("\t" + specialist.getEmail());
                    System.out.println("\t" + specialist.getCost());
                });

                for (AdvancedItem.AdditionalDetail detail : tmp.getAdditionalDetails()) {
                    System.out.println("\t" + "\t" + detail.getName());
                    System.out.println("\t" + "\t" + detail.getDescription());
                    System.out.println("\t" + "\t" + detail.getCost());
                }

            }
        });

    }

}