package home.maintenance.dao;

import home.maintenance.config.PersistenceConfigTest;
import home.maintenance.model.AdvancedItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * Created by Buibi on 09.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
public class ItemDaoTest {

    @Autowired
    private ItemDao dao;

    @Test
    public void getAll() throws Exception {
        dao.getAll().forEach(item -> {
            System.out.println(item);
            if (item instanceof AdvancedItem) {
                System.out.print(" => advanced");
                AdvancedItem tmp = (AdvancedItem) item;
                Optional<AdvancedItem.Specialist> optional = Optional.of(tmp.getSpecialist());
                optional.ifPresent(specialist -> {
                    System.out.println(specialist.getCompany());
                    System.out.println(specialist.getPhone());
                    System.out.println(specialist.getEmail());
                    System.out.println(specialist.getCost());
                });

            }
        });
    }

}