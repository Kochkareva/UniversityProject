package com.ulstu.University.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.ulstu.University.user.controller.UserSignupMvcController;
import com.ulstu.University.user.model.UserRole;
import com.ulstu.University.user.service.UserService;

import static com.ulstu.University.user.model.UserRole.TEACHER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
    private static final String LOGIN_URL = "/login";
    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
        createAdminOnStartup();
    }

    private void createAdminOnStartup() {
        final String admin = "admin";
        if (userService.findByLogin(admin) == null) {
            log.info("Admin user successfully created");
            userService.createUser(admin, admin, admin, UserRole.ADMIN);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(UserSignupMvcController.SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN_URL).permitAll() //отсутсвие проверки на права доступа
               .antMatchers(HttpMethod.GET, "/swagger-ui/index.html").hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * разрешает беспроблемный доступ к ресурсам
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/templates/**")
                .antMatchers("/webjars/**");
    }
}
