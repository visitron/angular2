package home.maintenance.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String email;
    @Column
    @Type(type = "yes_no")
    private boolean photo;
    @Column
    private String hash;
    @Column
    @Enumerated(EnumType.STRING)
    private UserState state;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    public User() {}

    public User(String firstName, String secondName, String email, boolean hasPhoto, String hash, Role role) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.photo = hasPhoto;
        this.hash = hash;
        this.role = role;
        this.creationDate = new Date();
        this.modificationDate = new Date();
        this.state = UserState.DRAFT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public boolean hasPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }
}
