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
public class Job extends Task {
    @Column
    private int difficulty;
    @Column
    private String target;

    public Job() {}

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
