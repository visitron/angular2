package home.maintenance.service;

import home.maintenance.dao.common.TaskRepository;
import home.maintenance.model.Task;
import home.maintenance.model.TaskState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static home.maintenance.model.TaskAction.EXPIRE;
import static home.maintenance.model.TaskState.ACTIVE;
import static home.maintenance.model.TaskState.CANCELLED;

@Slf4j
@Service
public class Jobs {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskStateGraph taskStateGraph;

    @Transactional
    @Scheduled(initialDelay = 30_000, fixedRate = 30_000)
    public void expire() {
        List<Task> tasks = taskRepository.findAllByState(ACTIVE);
        tasks.forEach(task -> taskStateGraph.next(ACTIVE, EXPIRE, this::isExpiring, task, this::doExpire));
    }

    @Transactional
    @Scheduled(initialDelay = 30_000, fixedRate = 30_000)
    public void remove() {
        List<Task> tasks = taskRepository.findAllByState(CANCELLED);
        tasks.forEach(task -> log.info("{} has been removed by system", task));
        taskRepository.deleteInBatch(tasks);
    }

    private boolean isExpiring(Task task) {
        return task.getSchedule() == null;
    }

    private void doExpire(TaskState state, Task task) {
        task.setState(state);
        task.setSchedule(null);
        log.info("{} has been expired by system", task.toString());
    }

}
