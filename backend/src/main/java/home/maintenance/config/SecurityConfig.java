package home.maintenance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.stream.Collectors;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .rolePrefix("ROLE_")
                .usersByUsernameQuery("SELECT USERNAME, HASH AS PASSWORD, CASE WHEN STATE = 'ACTIVE' THEN 1 ELSE 0 END AS ENABLED FROM \"USER\" WHERE USERNAME = ?")
                .authoritiesByUsernameQuery("SELECT USERNAME, ROLE FROM \"USER\" WHERE USERNAME = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/login/users").permitAll()
                .antMatchers("/login/auth").permitAll()
                .antMatchers("/admin/users").authenticated()
                .and()
                .formLogin()
                .loginPage("http://localhost:3000/login")
                .loginProcessingUrl("/login/auth")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.addHeader("Role", authentication.getAuthorities().stream().map(authority -> authority.getAuthority().substring(5)).collect(Collectors.joining(", ")));
                })
                .failureHandler((request, response, exception) -> {
                    if (exception instanceof DisabledException) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Your account has been locked or hasn't been approved yet.");
                    } else if (exception instanceof BadCredentialsException) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid password. Please try again.");
                    }
                })
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Access-Control-Allow-Credentials");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Role");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
