package home.maintenance.controller;

import home.maintenance.dao.common.TaskRepository;
import home.maintenance.model.AbstractTask;
import home.maintenance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by vsoshyn on 25/10/2016.
 */
@RestController
@RequestMapping(value = "/tasks", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Secured("ROLE_VIEW")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping
    public List<AbstractTask> getTasks(@RequestParam("includeShared") boolean includeShared, @AuthenticationPrincipal User user) throws Exception {
        return includeShared ? taskRepository.findAllByOwnerIdOrShared(user.getId(), true) : taskRepository.findAllByOwnerId(user.getId());
    }

    @RequestMapping(value = "/shared")
    public List<AbstractTask> getSharedTasks() throws Exception {
        return taskRepository.findAllByShared(true);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createTask(@RequestBody AbstractTask task, @AuthenticationPrincipal User user) throws Exception {
        task.setOwner(user);
        taskRepository.save(task);
        return ResponseEntity.ok().build();
    }


}
