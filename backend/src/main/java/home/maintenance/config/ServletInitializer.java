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
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        container.addListener(new ContextLoaderListener(rootContext));

        rootContext.register(WebConfig.class);
        rootContext.register(ApplicationConfig.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", DispatcherServlet.class);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
