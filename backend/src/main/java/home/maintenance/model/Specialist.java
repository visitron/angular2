package home.maintenance.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Embeddable
@Table
public class Specialist {
    @Column
    private String name;
    @Column
    private String company;
    /**
     * Has to start from 0
     */
    @Column
    private int phone;
    /**
     * All prices and costs are measured in cents (x100)
     */
    @Column
    private int cost;

    public Specialist() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
