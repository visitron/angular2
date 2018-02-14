package home.maintenance.service;

import home.maintenance.model.Task;
import home.maintenance.model.TaskAction;
import home.maintenance.model.TaskState;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

import static home.maintenance.model.TaskAction.ASSIGN;
import static home.maintenance.model.TaskAction.CANCEL;
import static home.maintenance.model.TaskAction.COMPLETE;
import static home.maintenance.model.TaskAction.EXPIRE;
import static home.maintenance.model.TaskAction.REMOVE;
import static home.maintenance.model.TaskAction.UNASSIGN;
import static home.maintenance.model.TaskState.ACTIVE;
import static home.maintenance.model.TaskState.CANCELLED;
import static home.maintenance.model.TaskState.DONE;
import static home.maintenance.model.TaskState.EXPIRED;
import static home.maintenance.model.TaskState.OPENED;
import static home.maintenance.model.TaskState.REMOVED;

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
    }

    @Override
    protected BiConsumer<TaskState, Task> defaultAction() {
        return action;
    }
}
