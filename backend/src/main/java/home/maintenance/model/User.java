package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import home.maintenance.view.UserView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table(name = "[USER]")
@org.hibernate.annotations.DynamicUpdate

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@NoArgsConstructor
@Getter @Setter
@ToString(doNotUseGetters = true, of = {"id", "username", "state"})
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
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean photo;
    @JsonIgnore
    @Column
    private String password;
    @JsonView(UserView.UI.class)
    @Column
    @Enumerated(EnumType.STRING)
    private UserState state;
    @JsonView(UserView.UI.class)
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Collection<Authority> authorities = new HashSet<>();
    @JsonIgnore
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JsonIgnore
    @Column(insertable = false)
    @org.hibernate.annotations.UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

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
    public Collection<Authority> getAuthorities() {
        return authorities;
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
}
