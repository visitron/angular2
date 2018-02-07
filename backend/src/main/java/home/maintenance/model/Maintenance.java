package home.maintenance.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "task")

@Getter @Setter
public class Maintenance extends AbstractTask {
    @Column(name = "m_cost")
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
}
