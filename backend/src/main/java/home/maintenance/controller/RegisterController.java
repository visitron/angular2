package home.maintenance.controller;

import home.maintenance.dao.common.CartRepository;
import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.Authority;
import home.maintenance.model.Cart;
import home.maintenance.model.User;
import home.maintenance.service.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity request(@RequestParam(required = false) MultipartFile image,
                                  @RequestParam String username,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String email,
                                  @RequestParam String password) throws IOException {

        User user = new User(username, firstName, lastName, email, image != null, passwordEncoder.encode(password), Arrays.asList(Authority.TASK_VIEW));
        if (userRepository.countByUsername(username) != 0) {
            return ResponseEntity.badRequest().body("Such username is already exist. Try another one.");
        }

        userRepository.save(user);

        Cart cart = new Cart();
        cart.setOwner(user);

        cartRepository.save(cart);

        if (image != null) {
            imageRepository.save(image.getBytes(), user.getId());
            System.out.println("New user is created");
        }
        return new ResponseEntity(HttpStatus.OK);
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
