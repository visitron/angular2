package home.maintenance.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by vsoshyn on 28/10/2016.
 */
public class ServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class);

//         Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
//        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
//        dispatcherContext.register(WebConfig.class);
        rootContext.register(WebConfig.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", DispatcherServlet.class);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
