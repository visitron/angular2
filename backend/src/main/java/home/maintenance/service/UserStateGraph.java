package home.maintenance.service;

import home.maintenance.model.AdminAction;
import home.maintenance.model.User;
import home.maintenance.model.UserState;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

import static home.maintenance.model.AdminAction.*;
import static home.maintenance.model.UserState.*;

/**
 * Created by Buibi on 29.01.2017.
 */
@Component
public class UserStateGraph extends StateGraph<UserState, AdminAction, User> {
    private BiConsumer<UserState, User> action = (state, user) -> user.setState(state);

    public UserStateGraph() {
        add(DRAFT, ACTIVE, APPROVE);
        add(ACTIVE, BLOCKED, BLOCK);
        add(BLOCKED, ACTIVE, UNBLOCK);
    }

    @Override
    protected BiConsumer<UserState, User> defaultAction() {
        return action;
    }
}
