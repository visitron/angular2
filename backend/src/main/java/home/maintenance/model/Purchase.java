package home.maintenance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "fk_task")

@Getter @Setter
@ToString(doNotUseGetters = true)
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
}
