package home.maintenance.dao.common;

import home.maintenance.model.AbstractTask;
import home.maintenance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<AbstractTask, Long> {
    List<AbstractTask> findAllByOwner(User owner);
    List<AbstractTask> findAllByOwnerAndShared(User owner, boolean shared);
    List<AbstractTask> findAllByShared(boolean shared);
}
