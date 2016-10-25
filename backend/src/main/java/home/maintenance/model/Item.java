package home.maintenance.model;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Entity
public class Item {
    private String name;
    private Date lastMaintenanceDate;
    private List<String> info;

    public Item() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
