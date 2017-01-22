package home.maintenance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "fk_task")
public class Payment extends Task {
    @Column
    private int cost;
    @Column
    private String target;

    public Payment() {}

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}