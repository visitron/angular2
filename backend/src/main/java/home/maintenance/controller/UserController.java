package home.maintenance.controller;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.User;
import home.maintenance.service.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@Controller
@RequestMapping("/user")
@Secured("TASK_VIEW")

@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "/{id}/message", method = RequestMethod.POST)
    public ResponseEntity message(@PathVariable(name = "id") long id, @RequestBody String body, @AuthenticationPrincipal User user) {
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity update(@RequestBody User user) {
        User updatedUser = userRepository.findById(user.getId()).get();
        updatedUser.patch(user);
        return ResponseEntity.noContent().build();
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity delete(@AuthenticationPrincipal User user) {
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @RequestMapping(value = "/changePhoto", method = RequestMethod.POST)
    public ResponseEntity request(@RequestParam(required = false, name = "image") MultipartFile image, @AuthenticationPrincipal User user) throws IOException {
        user = userRepository.findById(user.getId()).get();

        if (image != null) {
            imageRepository.save(image.getBytes(), user.getId());
            user.setPhoto(true);
        } else {
            imageRepository.delete(user.getId());
            user.setPhoto(false);
        }
        return ResponseEntity.noContent().build();
    }

}
