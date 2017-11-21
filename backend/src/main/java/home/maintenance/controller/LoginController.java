package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@RequestMapping("/login")
@Secured("ROLE_USER_MANAGEMENT")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    @ResponseBody public List<User> users() {
        return userRepository.findAll();
    }

}
