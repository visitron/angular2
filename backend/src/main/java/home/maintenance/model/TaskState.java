package home.maintenance.model;

/**
 * Created by Buibi on 21.01.2017.
 */
public enum TaskState {
    OPENED, //just created, nobody gets it into the cart. After dropping back to the unassigned tasks list it should have this state as well
    CANCELLED, //was created by mistake and should be scheduled for removal
    DONE, //the task has bees accomplished and cannot be reopened for further activities. The new one may be cloned from it instead
    ACTIVE, //tracked by someone among registered users
    EXPIRED, //due date has been reached. Such a tack may be rescheduled, cancelled or collected by the cleaning job depending on retention policy
    REMOVED, //should be removed right after setting this state
}
