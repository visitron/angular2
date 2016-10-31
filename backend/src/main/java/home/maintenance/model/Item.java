package home.maintenance.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Entity
@Table
public class Item {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItem")
    @SequenceGenerator(name = "seqItem", sequenceName = "SEQ_ITEM")
    private long id;
    @Column(name = "NAME", length = 64, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = 128)
    private String description;
    @Column(name = "LIFECYCLE")
    private int lifecycle;
    @Temporal(TemporalType.DATE)
    @Column(name = "MAINTENANCE_DATE", nullable = false)
    private Date maintenanceDate;

    @Transient
    private List<String> info;

    public Item() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(int lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
