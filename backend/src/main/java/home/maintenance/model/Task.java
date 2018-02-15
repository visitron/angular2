package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import home.maintenance.view.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Maintenance.class, name = "maintenance"),
        @JsonSubTypes.Type(value = Payment.class, name = "payment"),
        @JsonSubTypes.Type(value = Purchase.class, name = "purchase")
})

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString(doNotUseGetters = true, of = {"id", "shared", "state"})
public class Task extends Auditable {
    @JsonView(Views.CartView.class)
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private Long id;
    @Column
    private String schedule;
    @Column
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column
    @Type(type = "yes_no")
    private boolean shared;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskState state = TaskState.OPENED;
    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Column
    private String target;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Task parent;
    @Column
    @Lob
    private byte[] image;
    @JsonIgnore
    @ManyToMany(mappedBy = "tasks")
    private List<Cart> carts = new ArrayList<>();
}
