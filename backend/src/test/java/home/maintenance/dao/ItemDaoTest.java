package home.maintenance.dao;

import home.maintenance.config.PersistenceConfigTest;
import home.maintenance.model.AdvancedItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Buibi on 09.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
public class ItemDaoTest {

    @Autowired
    private ItemDao dao;

    //todo subselect ??

    @Test
    public void getAll() throws Exception {
        dao.getAll().forEach(item -> {
            System.out.println(item);
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