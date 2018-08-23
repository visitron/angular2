package home.maintenance.service;

import home.maintenance.model.Task;
import home.maintenance.model.TaskAction;
import home.maintenance.model.TaskState;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

import static home.maintenance.model.TaskAction.*;
import static home.maintenance.model.TaskState.*;

/**
 * Created by Buibi on 29.01.2017.
 */
@Component
public class TaskStateGraph extends StateGraph<TaskState, TaskAction, Task> {
    private BiConsumer<TaskState, Task> action = (state, task) -> task.setState(state);

    public TaskStateGraph() {
        add(OPENED, CANCELLED, CANCEL);
        add(OPENED, ACTIVE, ASSIGN);
        add(ACTIVE, OPENED, UNASSIGN);
        add(ACTIVE, DONE, COMPLETE);
        add(ACTIVE, CANCELLED, CANCEL);
        add(OPENED, REMOVED, REMOVE);
        add(ACTIVE, EXPIRED, EXPIRE);
        add(ACTIVE, ACTIVE, POSTPONE);
        add(EXPIRED, ACTIVE, POSTPONE);
    }

    @Override
    protected BiConsumer<TaskState, Task> defaultAction() {
        return action;
    }
}
