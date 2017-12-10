package home.maintenance.config;

import home.maintenance.dao.common.UserRepository;
import home.maintenance.model.Authority;
import home.maintenance.model.User;
import home.maintenance.model.UserState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${application.security.init.username}")
    private String username;
    @Value("${application.security.init.password}")
    private String password;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication();
        builder.userDetailsService(new CustomUserDetailService())
                .passwordEncoder(passwordEncoder);
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
                .antMatchers("/tasks").authenticated()
                .antMatchers("/register/requestAdmin").authenticated()
                .and()
                .formLogin()
                .loginPage("http://localhost:3000/login")
                .loginProcessingUrl("/login/auth")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.addHeader("Authority",
                            authentication.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.joining(","))
                    );
                })
                .failureHandler((request, response, exception) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getLocalizedMessage()))
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
        configuration.addExposedHeader("Authority");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @PostConstruct
    private void postConstruct() {
        if (userRepository.count() > 0) return;

        String username = this.username;
        String password = this.password;

        if (password == null) password = UUID.randomUUID().toString();

        User su = new User(username, null, null, null, false, passwordEncoder.encode(password), Collections.singletonList(Authority.ADMIN_MANAGEMENT));
        su.setState(UserState.ACTIVE);
        userRepository.save(su);
        StringBuilder welcomeBuilder = new StringBuilder().append("\n\n\tThis is the first run of the application. Credentials for login:")
                .append("\n\t\tusername: admin")
                .append("\n\t\tpassword: ").append(password)
                .append("\n\tPlease update password right after login!\n");
        log.info(welcomeBuilder.toString());
    }

    class CustomUserDetailService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return ofNullable(userRepository.findByUsername(username))
                    .orElseThrow(() -> new UsernameNotFoundException("User [" + username + "] is not found"));
        }
    }

}
