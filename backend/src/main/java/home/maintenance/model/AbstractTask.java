package home.maintenance.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractTask {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column
    private String schedule;
    @Column
    @Type(type = "yes_no")
    private boolean shared;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskState state;
    @Column
    private int priority;
    @Column
    private String target;
    @ManyToOne
    private User owner;

    protected AbstractTask() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
