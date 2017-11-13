package home.maintenance.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table(name = "[STATISTIC]")
public class Statistic {

    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @ManyToOne
    private AbstractTask abstractTask;
    @Column
    private int cost;
    @Column(name = "[DATE]")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column
    private String action;

    public Statistic() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AbstractTask getAbstractTask() {
        return abstractTask;
    }

    public void setAbstractTask(AbstractTask abstractTask) {
        this.abstractTask = abstractTask;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
