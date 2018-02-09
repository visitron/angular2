package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "task")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
public class Maintenance extends Task {
    @Column(name = "m_cost")
    private int cost;
    @Column
    @Type(type = "yes_no")
    private boolean requiresSpecialist;
    @Embedded
    private Specialist specialist;
}
