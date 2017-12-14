package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import home.maintenance.view.UserView;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

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
    @JsonView(UserView.Name.class)
    @Column(unique = true)
    private String username;
    @JsonView(UserView.UI.class)
    @Column
    private String firstName;
    @JsonView(UserView.UI.class)
    @Column
    private String lastName;
    @JsonIgnore
    @Column
    private String email;
    @Column
    @Type(type = "yes_no")
    private boolean photo;
    @JsonIgnore
    @Column
    private String password;
    @JsonView(UserView.UI.class)
    @Column
    @Enumerated(EnumType.STRING)
    private UserState state;
    @JsonIgnore
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Authority> authorities = new ArrayList<>();
    @JsonIgnore
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JsonIgnore
    @Column(insertable = false)
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    public User() {}

    public User(String username, String firstName, String lastName, String email, boolean hasPhoto, String password, List<Authority> authorities) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photo = hasPhoto;
        this.authorities.addAll(authorities);
        this.state = UserState.DRAFT;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return state == UserState.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return state == UserState.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void addAuthorities(List<Authority> authorities) {
        authorities.removeAll(this.authorities);
        this.authorities.addAll(authorities);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }
}
