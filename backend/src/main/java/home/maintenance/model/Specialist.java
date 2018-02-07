package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Embeddable
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Specialist {
    @Column
    private String name;
    @Column
    private String company;
    @Column
    private int phone;
    /**
     * All prices and costs are measured in coins (x100)
     */
    @Column
    private int cost;
}
