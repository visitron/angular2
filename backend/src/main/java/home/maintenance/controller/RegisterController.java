package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.Authority;
import home.maintenance.model.User;
import home.maintenance.service.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    private boolean initialized;

    @Transactional
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity request(@RequestParam(required = false) MultipartFile image,
                                  @RequestParam String username,
                                  @RequestParam String firstName,
                                  @RequestParam String secondName,
                                  @RequestParam String email,
                                  @RequestParam String password) throws IOException {

        if (!initialized) {
            return ResponseEntity.badRequest().body("There is no admin registered. Further work is prohibited.");
        }

        User user = new User(username, firstName, secondName, email, image != null, password, Arrays.asList(Authority.USER));
        if (userRepository.countByUsername(username) != 0) {
            return ResponseEntity.badRequest().body("Such username is already exist. Try another one.");
        }

        userRepository.save(user);
        if (image != null) {
            imageRepository.save(image.getBytes(), user.getId());
            System.out.println("New user is created");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/requestAdmin", method = RequestMethod.POST)
    public ResponseEntity registerAdmin() {
        initialized = true;
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostConstruct
    private void initialize() {
        initialized = true;
//        initialized = userRepository.count() > 0;
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity handleError(HttpServletRequest req, Exception ex) {
        String message = ex.getMessage();
        if (message.contains("ConstraintViolationException")) {
            message = "It seems somebody has just registered specified username";
        }

        return ResponseEntity.badRequest().body(message);
    }

}
