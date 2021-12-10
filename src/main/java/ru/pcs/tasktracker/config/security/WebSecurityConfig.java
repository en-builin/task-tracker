package ru.pcs.tasktracker.config.security;

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
import ru.pcs.tasktracker.utils.SecurityUtils;
import ru.pcs.tasktracker.utils.WebUtils;

import javax.sql.DataSource;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@EnableWebSecurity
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                        .antMatchers(WebUtils.PERMIT_ALL_URLS).permitAll()
                        .antMatchers(WebUtils.AUTHENTICATED_URLS).authenticated()
                        .antMatchers(WebUtils.ADMIN_URLS).hasAuthority("ADMIN")
                        .anyRequest().denyAll()
                        .and()
                .formLogin()
                        .loginPage(WebUtils.URL_SIGN_IN)
                        .loginProcessingUrl(WebUtils.URL_SIGN_IN)
                        .defaultSuccessUrl("/", true)
                        .failureUrl(WebUtils.URL_SIGN_IN + "?error=true")
                        .usernameParameter(SecurityUtils.USERNAME_PARAMETER)
                        .passwordParameter(SecurityUtils.PASSWORD_PARAMETER)
                        .permitAll()
                        .and()
                .rememberMe()
                        .key(SecurityUtils.REMEMBER_ME_KEY)
                        .rememberMeParameter(SecurityUtils.REMEMBER_ME_PARAMETER)
                        .alwaysRemember(true)
                        .tokenRepository(tokenRepository())
                        .tokenValiditySeconds(SecurityUtils.TOKEN_VALIDITY_SECONDS)
                        .and()
                .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher(WebUtils.URL_SIGN_OUT))
                        .logoutSuccessUrl(WebUtils.URL_SIGN_IN)
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
