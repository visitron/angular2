package home.maintenance.vo;

import home.maintenance.model.Role;
import home.maintenance.model.User;

/**
 * Created by Buibi on 21.01.2017.
 */
public class SimpleUserVO {
    public long id;
    public String firstName;
    public String secondName;
//    public byte[] image;
    public Role role;

    public SimpleUserVO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        secondName = user.getSecondName();
//        image = user.getImage();
        role = user.getRole();
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
