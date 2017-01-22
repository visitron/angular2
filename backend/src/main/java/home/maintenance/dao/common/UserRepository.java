package home.maintenance.dao.common;

import home.maintenance.model.Role;
import home.maintenance.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRole(Role role);

    default boolean adminExists() {
        List<User> admins = findByRole(Role.ADMIN);
        return !admins.isEmpty();
    }
}
