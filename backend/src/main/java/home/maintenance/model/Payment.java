package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonView;
import home.maintenance.view.UserView;

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
public class Payment extends AbstractTask {
    @Column
    private int cost;

    public Payment() {}

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
