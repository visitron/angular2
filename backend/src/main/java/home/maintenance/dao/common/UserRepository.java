package home.maintenance.dao.common;

import home.maintenance.model.User;
import home.maintenance.model.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    int countByUsername(String username);
    List<User> findAllByState(UserState state);
}
