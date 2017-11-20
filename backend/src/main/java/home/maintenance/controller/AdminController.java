package home.maintenance.controller;

import home.maintenance.dao.common.ConfigRepository;
import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.*;
import home.maintenance.service.UserStateGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private UserStateGraph graph;

    @RequestMapping(value = "/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/audit")
    public List<Audit> audit() {
        return Collections.emptyList();
    }

    @Transactional
    @RequestMapping(value = "/users/action/{action}", method = RequestMethod.POST)
    public ResponseEntity doAction(@RequestBody List<Long> ids, @PathVariable AdminAction action) {
        if (ids == null || ids.isEmpty()) return new ResponseEntity<>("None of records was chosen", HttpStatus.BAD_REQUEST);
        List<User> users = userRepository.findAll(ids);
        if (users.stream().map(User::getState).distinct().count() > 1) return new ResponseEntity<>("Chosen users have not the same state", HttpStatus.BAD_REQUEST);

        UserState nextState = graph.next(users.get(0).getState(), action);
        if (nextState == null) return new ResponseEntity<>("An action under chosen transition is not supported", HttpStatus.BAD_REQUEST);

        users.forEach(user -> user.setState(nextState));
        userRepository.save(users);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/config/get")
    public List<Config> getConfig() {
        return configRepository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/config/action/{action}", method = RequestMethod.POST)
    public ResponseEntity saveConfig(@RequestBody List<Config> config) {
        configRepository.save(config);
        return ResponseEntity.ok(null);
    }

}
