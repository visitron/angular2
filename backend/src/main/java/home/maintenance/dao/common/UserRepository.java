package home.maintenance.dao.common;

import home.maintenance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    int countByUsername(String username);
}
