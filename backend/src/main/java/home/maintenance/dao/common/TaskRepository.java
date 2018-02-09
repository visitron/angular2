package home.maintenance.dao.common;

import home.maintenance.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOwnerId(long ownerId);
    List<Task> findAllByOwnerIdOrShared(long owner, boolean shared);
    List<Task> findAllByShared(boolean shared);
}
