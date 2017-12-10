package home.maintenance.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @OneToOne
    private User user;
    @ManyToOne
    private AbstractTask task;
    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date maturityDate;

    public Cart() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AbstractTask getTask() {
        return task;
    }

    public void setTask(AbstractTask task) {
        this.task = task;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}
