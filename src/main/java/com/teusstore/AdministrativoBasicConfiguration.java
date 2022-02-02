package com.teusstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(2)
public class AdministrativoBasicConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT email AS username, senha AS password, 1 AS ENABLE FROM employees WHERE email=?")
                .authoritiesByUsernameQuery(
                        "SELECT e.email AS username, r.nome AS authority FROM permissions p INNER JOIN employees e ON p.funcionario_id = e.id INNER JOIN role r ON p.papel_id = r.id WHERE e.email=?;")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/administrativo/cadastrar/**").hasAuthority("gerente")
                .antMatchers("/administrativo/**").authenticated()
                .and().formLogin().loginPage("/login").failureUrl("/login")
                .loginProcessingUrl("/admin").defaultSuccessUrl("/administrativo").usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/administrativo/logout"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/negadoAdministrativo")
                .and().csrf().disable();
    }
}
