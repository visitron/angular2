package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.Role;
import home.maintenance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity request(@RequestParam(required = false) MultipartFile image,
                                      @RequestParam String firstName,
                                      @RequestParam String secondName,
                                      @RequestParam String email,
                                      @RequestParam String password) throws IOException {

        boolean isAdminExists = userRepository.adminExists();
        User user = new User(firstName, secondName, email, password, image == null ? null : image.getBytes(), isAdminExists ? Role.USER : Role.ADMIN);
        userRepository.save(user);
        return new ResponseEntity<>(user.getRole(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleStorageFileNotFound(Exception exc) {
        exc.printStackTrace();
        return ResponseEntity.notFound().build();
    }

}
