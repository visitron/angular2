package home.maintenance.vo;

/**
 * Created by Buibi on 21.01.2017.
 */
public class UserVO extends SimpleUserVO {
    public String email;
    public String password;

    @Override
    public String toString() {
        return "UserVO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
