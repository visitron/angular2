package home.maintenance.model;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table(name = "[USER]")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @Column(unique = true)
    private String username;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Authority> authority = new ArrayList<>();
    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    public User() {}

    public User(String username, String firstName, String secondName, String email, boolean hasPhoto, String hash, Authority authority) {
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.photo = hasPhoto;
        this.hash = hash;
        this.authority.add(authority);
        this.creationDate = new Date();
        this.modificationDate = new Date();
        this.state = authority == Authority.ADMIN ? UserState.ACTIVE : UserState.DRAFT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return hash;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
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
