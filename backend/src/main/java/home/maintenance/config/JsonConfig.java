package home.maintenance.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import home.maintenance.model.*;
import home.maintenance.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class JsonConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void configureObjectMapper() throws JsonProcessingException {
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

        User user = new User("name", "first", "second", "@mail", false, "pass", Arrays.asList(Authority.ADMIN_MANAGEMENT));
        AbstractTask task = new Payment();
        task.setOwner(user);
        task.setState(TaskState.OPENED);
        task.setId(1);

        System.out.println((objectMapper.writerWithView(UserView.UI.class).writeValueAsString(task)));
    }

}
