package home.maintenance.model;

import javax.persistence.*;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "fk_task")
public class Purchase extends AbstractTask {
    @Column
    private int price;
    @Column
    private int amount;
    @OneToOne
    private Product product;
    @Column
    @Enumerated(EnumType.STRING)
    private Unit unit;

    public Purchase() {}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
