package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Cart {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @OneToOne
    private User user;
    @ManyToOne
    private AbstractTask task;
    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date maturityDate;
}
