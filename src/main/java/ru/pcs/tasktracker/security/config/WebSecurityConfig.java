package ru.pcs.tasktracker.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user@r.ru").password("userpass").roles("USER")
//                .and()
//                .withUser("admin@r.ru").password("adminpass").roles("ADMIN");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/sign-in", "/sign-up").permitAll()
                        .antMatchers("/css/*").permitAll()
                        .antMatchers("/js/*").permitAll()
                        //.antMatchers("/sign-in").permitAll()
    //                    .antMatchers("/signUp").permitAll()
//                        .antMatchers("/").authenticated()
    //                    .antMatchers("/profile").authenticated()
    //                    .antMatchers("/accounts/**").hasAuthority("ADMIN")
                        .antMatchers("/users").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                .formLogin()
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/sign-in")
                        .defaultSuccessUrl("/")
                        .failureUrl("/sign-in?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")

                        .permitAll()
                        .and()
                .rememberMe()
                        .key("a52378957b9579d56e827ac3e4a0281f")
                        .rememberMeParameter("remember-me")
                        .alwaysRemember(true)
                        .tokenRepository(tokenRepository())
                        .tokenValiditySeconds(60 * 60 * 24 * 365)
                        .and()
                .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/sign-out"))
                        .logoutSuccessUrl("/sign-in")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
        ;
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
