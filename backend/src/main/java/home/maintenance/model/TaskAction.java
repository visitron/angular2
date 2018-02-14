package home.maintenance.model;

/**
 * Created by Buibi on 21.01.2017.
 */
public enum TaskAction {
    ASSIGN, //adding to the cart. Only shared tasks can have several assignees
    UNASSIGN, //unassigning from the task. If it is not in any cart, it'll be moved back to unallocated tasks list
    CANCEL, //cancels the task
    REMOVE, //only tasks in OPENED state can be removed
    COMPLETE, //marks the task as done
    EXPIRE, //a job is trying to expire the task. Should be considered the other checks
}
