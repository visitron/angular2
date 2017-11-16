package home.maintenance.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Buibi on 21.01.2017.
 */
public enum Authority implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
