package home.maintenance.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "task")
public class Maintenance extends Task {
    @Column
    private int cost;
    @ManyToOne
    private Maintenance parent;
    @Column
    @Type(type = "yes_no")
    private boolean requiresSpecialist;
    @Embedded
    private Specialist specialist;
    @Column
    private int difficulty;
    @Column
    @Lob
    private byte[] image;
    @Column
    private String target;

    public Maintenance() {}

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Maintenance getParent() {
        return parent;
    }

    public void setParent(Maintenance parent) {
        this.parent = parent;
    }

    public boolean isRequiresSpecialist() {
        return requiresSpecialist;
    }

    public void setRequiresSpecialist(boolean requiresSpecialist) {
        this.requiresSpecialist = requiresSpecialist;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
