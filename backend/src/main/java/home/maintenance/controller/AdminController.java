package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.Audit;
import home.maintenance.vo.SimpleUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users")
    public List<SimpleUserVO> users() {
//        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());

        List<SimpleUserVO> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(new SimpleUserVO(user)));

        return users;
    }

    @RequestMapping(value = "/audit")
    public List<Audit> audit() {
        return Collections.emptyList();
    }


}
