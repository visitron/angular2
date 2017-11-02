package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.vo.SimpleUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
        return userRepository.findAll().stream().map(SimpleUserVO::new).collect(toList());
    }

}
