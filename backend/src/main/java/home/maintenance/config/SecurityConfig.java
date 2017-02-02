package home.maintenance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("Vladimir").password("1").roles("ADMIN").and()
                .withUser("Marina").password("2").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> System.out.println(request))
//                .and()
                .authorizeRequests()
                .antMatchers("/login/users").permitAll()
                .antMatchers("/login/auth").permitAll()
//                .antMatchers("/admin/users/**").permitAll()
                .antMatchers("/admin/users").authenticated()
                .and()
                .formLogin()
                .loginPage("http://localhost:3000/login")
                .loginProcessingUrl("/login/auth")
                .successHandler((request, response, authentication) -> {
                    System.out.println("Successfully authenticated");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("Authentication failed");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .and()
//                .httpBasic()
//                .and()
                .rememberMe().authenticationSuccessHandler((request, response, authentication) -> {
                    System.out.println("Remember-me success");
                }).rememberMeParameter("remember-me")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler((request, response, authentication) -> {
                    Cookie c = new Cookie("JSESSIONID", null);
                    c.setHttpOnly(true);
                    c.setPath("/");
                    c.setMaxAge(0);
                    response.addCookie(c);
                    for (int i = 0; request.getCookies() != null && i < request.getCookies().length; i++) {
                        request.getCookies()[i].setValue(null);
                        request.getCookies()[i].setMaxAge(0);
                    }
                    System.out.println("Logout");
                });
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("Access");
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Access-Control-Allow-Credentials");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Cache-Control");
        configuration.addAllowedHeader("X-XSS-Protection");
        configuration.addAllowedHeader("Set-Cookie");
        configuration.addAllowedHeader("Content-Length");
        configuration.addAllowedHeader("Date");
        configuration.addAllowedHeader("Expires");
        configuration.addAllowedHeader("Location");
        configuration.addAllowedHeader("Connection");
        configuration.addAllowedHeader("Host");
        configuration.addAllowedHeader("Pragma");
        configuration.addAllowedHeader("Vary");
        configuration.addAllowedHeader("X-Content-Type-Options");
        configuration.addAllowedHeader("X-Frame-Options");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
