package home.maintenance.dao.common;

import home.maintenance.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByOwnerId(Long id);
}
