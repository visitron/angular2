package home.maintenance.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Buibi on 21.01.2017.
 */
public enum Authority implements GrantedAuthority {
    USER_MANAGEMENT, TASK_MANAGEMENT, TASK_VIEW;

    @Override
    public String getAuthority() {
        return name();
    }
}
