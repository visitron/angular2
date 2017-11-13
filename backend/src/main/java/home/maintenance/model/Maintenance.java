package home.maintenance.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "task")
public class Maintenance extends AbstractTask {
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
    @Lob
    private byte[] image;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
