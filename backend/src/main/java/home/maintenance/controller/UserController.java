package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.Authority;
import home.maintenance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/requestAuthorities", method = RequestMethod.POST)
    public List<User> requestAuthorities(@RequestBody List<Authority> authorities, @AuthenticationPrincipal User user) {
        return Collections.emptyList();
    }

}
