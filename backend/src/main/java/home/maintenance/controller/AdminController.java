package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.AdminAction;
import home.maintenance.model.Authority;
import home.maintenance.model.User;
import home.maintenance.model.UserState;
import home.maintenance.service.UserStateGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@RequestMapping("/users")
@Secured({"USER_MANAGEMENT"})
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserStateGraph graph;

    @RequestMapping
    public List<User> getUsers(@RequestParam(required = false) UserState state) {
        return state != null ? userRepository.findAllByState(state) : userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "action/{action}", method = RequestMethod.POST)
    public ResponseEntity doAction(@RequestBody List<Long> ids, @PathVariable AdminAction action) {
        if (ids == null || ids.isEmpty()) return new ResponseEntity<>("None of records was chosen", HttpStatus.BAD_REQUEST);
        List<User> users = userRepository.findAll(ids);
        if (users.stream().map(User::getState).distinct().count() > 1) return new ResponseEntity<>("Chosen users have not the same state", HttpStatus.BAD_REQUEST);

        UserState nextState = graph.next(users.get(0).getState(), action);
        if (nextState == null) return new ResponseEntity<>("Incorrect requested user state", HttpStatus.BAD_REQUEST);

        users.forEach(user -> user.setState(nextState));
        userRepository.save(users);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @RequestMapping(value = "{userId}/authorities", method = RequestMethod.PATCH)
    public ResponseEntity addAuthorities(@PathVariable("userId") long userId, @RequestBody Collection<Authority> authorities) {
        User user = userRepository.findOne(userId);
        user.getAuthorities().clear();
        user.getAuthorities().addAll(authorities);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

}
