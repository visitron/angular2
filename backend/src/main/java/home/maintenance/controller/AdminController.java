package home.maintenance.controller;

import home.maintenance.dao.common.ConfigRepository;
import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.*;
import home.maintenance.service.UserStateGraph;
import home.maintenance.vo.SimpleUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private UserStateGraph graph;

    @RequestMapping(value = "/users")
    public List<SimpleUserVO> users() {
        List<SimpleUserVO> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(new SimpleUserVO(user)));

        return users;
    }

    @RequestMapping(value = "/audit")
    public List<Audit> audit() {
        return Collections.emptyList();
    }

    @Transactional
    @RequestMapping(value = "/users/action/{action}", method = RequestMethod.POST)
    public ResponseEntity doAction(@RequestBody List<Long> ids, @PathVariable AdminAction action) {
        Iterable<User> iterable = userRepository.findAll(ids);
        List<User> users = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        if (users.stream().map(User::getState).distinct().collect(Collectors.toSet()).size() > 1) return new ResponseEntity<>("Chosen users have not the same state", HttpStatus.BAD_REQUEST);

        UserState nextState = graph.next(users.get(0).getState(), action);
        if (nextState == null) return new ResponseEntity<>("An action under chosen transition is not supported", HttpStatus.BAD_REQUEST);

        iterable.forEach(user -> user.setState(nextState));
        userRepository.save(iterable);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/config/get")
    public List<Config> getConfig() {
        return StreamSupport.stream(configRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(value = "/config/save", method = RequestMethod.POST)
    public ResponseEntity saveConfig(List<Config> config) {
        configRepository.save(config);
        return ResponseEntity.ok(null);
    }

}
