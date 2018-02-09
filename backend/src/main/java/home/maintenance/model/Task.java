package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

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
import javax.persistence.ManyToOne;
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
@ToString(doNotUseGetters = true, of = {"id", "creationDate", "shared", "state"})
public class Task {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(insertable = false)
    @org.hibernate.annotations.UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modificationDate;
    @Column
    private String schedule;
    @Column
    @Type(type = "yes_no")
    private boolean shared;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskState state;
    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Column
    private String target;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Task parent;
    @Column
    @Lob
    private byte[] image;
}
