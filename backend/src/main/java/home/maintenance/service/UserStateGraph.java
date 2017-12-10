package home.maintenance.service;

import home.maintenance.model.AdminAction;
import home.maintenance.model.UserState;
import org.springframework.stereotype.Component;

/**
 * Created by Buibi on 29.01.2017.
 */
@Component
public class UserStateGraph extends StateGraph<UserState, AdminAction> {
    public UserStateGraph() {
        add(UserState.DRAFT, UserState.ACTIVE, AdminAction.APPROVE);
        add(UserState.ACTIVE, UserState.BLOCKED, AdminAction.BLOCK);
        add(UserState.BLOCKED, UserState.ACTIVE, AdminAction.UNBLOCK);
    }
}
