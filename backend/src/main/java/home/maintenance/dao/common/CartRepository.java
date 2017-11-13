package home.maintenance.dao.common;

import home.maintenance.model.Cart;
import home.maintenance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
