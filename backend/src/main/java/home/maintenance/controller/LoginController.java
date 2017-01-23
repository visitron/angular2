package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.vo.SimpleUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public List<SimpleUserVO> users() {
        List<SimpleUserVO> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(new SimpleUserVO(user)));
        return users;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String auth(@RequestParam String userName, @RequestParam String password) {

        return "Failed";
    }

}
