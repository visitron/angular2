package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.vo.SimpleUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    @ResponseBody public List<SimpleUserVO> users() {
        List<SimpleUserVO> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(new SimpleUserVO(user)));
        return users;
    }

}
