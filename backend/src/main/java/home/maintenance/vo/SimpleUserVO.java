package home.maintenance.vo;

import home.maintenance.model.Authority;
import home.maintenance.model.User;
import home.maintenance.model.UserState;

import java.util.List;

/**
 * Created by Buibi on 21.01.2017.
 */
public class SimpleUserVO {
    public long id;
    public String firstName;
    public String secondName;
    public boolean photo;
    public List<Authority> authority;
    public UserState state;

    public SimpleUserVO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        secondName = user.getSecondName();
        photo = user.hasPhoto();
        authority = user.getAuthority();
        state = user.getState();
    }

    @Override
    public String toString() {
        return "SimpleUserVO{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", authority=" + authority +
                '}';
    }
}
