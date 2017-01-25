package home.maintenance.vo;

import home.maintenance.model.Role;
import home.maintenance.model.User;
import home.maintenance.model.UserState;

/**
 * Created by Buibi on 21.01.2017.
 */
public class SimpleUserVO {
    public long id;
    public String firstName;
    public String secondName;
    public boolean photo;
    public Role role;
    public UserState state;

    public SimpleUserVO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        secondName = user.getSecondName();
        photo = user.hasPhoto();
        role = user.getRole();
        state = user.getState();
    }

    @Override
    public String toString() {
        return "SimpleUserVO{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", role=" + role +
                '}';
    }
}
