package com.example.demo.security;

import com.example.demo.auth.ApplicationUserDetailsService;
import com.example.demo.jwt.JwtConfiguration;
import com.example.demo.jwt.JwtSecretKey;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUserAndPasswordAuthenficationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    
   
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    
    private final JwtConfiguration jwtConfiguration;
    
    private final JwtSecretKey jwtSecretKey;
    
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserDetailsService applicationUserDetailsService, JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //basic auth
//        http
//                .csrf()
////                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                .and()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*")
//                .permitAll()
//                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
        
        
        // form auth
//        http
//                .csrf()
////                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                .and()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*")
//                .permitAll()
//                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                    .permitAll()
//                    .defaultSuccessUrl("/courses", true)
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .and()
//                .rememberMe()
//                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                    .key("key")
//                    .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout" , "GET"))
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID" , "remember-me")
//                    .logoutSuccessUrl("/login")
//        ;
    
              http
                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .disable()
                      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                      .and()
                      .addFilter(new JwtUserAndPasswordAuthenficationFilter(authenticationManager(), jwtConfiguration, jwtSecretKey))
                      .addFilterAfter(new JwtTokenVerifier(jwtConfiguration, jwtSecretKey) , JwtUserAndPasswordAuthenficationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                      .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated();
    }
    
      // InMemoryUserDetailsManager
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails yakoubOB = User.builder()
//                .username("y-ouldbouchiba")
//                .password(passwordEncoder.encode("password"))
//                //.roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("a-admin")
//                .password(passwordEncoder.encode("password"))
//                //.roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails trainee = User.builder()
//                .username("a-trainee")
//                .password(passwordEncoder.encode("password"))
//                //.roles(ADMINTRAINEE.name())
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(yakoubOB , admin , trainee);
//    }
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserDetailsService);
        return daoAuthenticationProvider;
    }
}
