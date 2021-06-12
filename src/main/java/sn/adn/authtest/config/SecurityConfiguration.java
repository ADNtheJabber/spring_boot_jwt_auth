package sn.adn.authtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sn.adn.authtest.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bcrypt);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/register").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Bean
    public BCryptPasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }
}
