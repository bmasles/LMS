package com.smoothstack.lms.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity(debug = true)
public class CommonSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.h2.console.enabled:false}")
    private boolean h2ConsoleEnabled;

    @Value("${spring.h2.console.path:/h2-console}")
    private String h2ConsolePath;


    @Autowired
    private CommonJwtConfigurer jwtConfigurer;

    @Autowired
    private CommonJwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
       authenticationManagerBuilder
               .inMemoryAuthentication().withUser("inmemory").password("password").roles("TEST_USER");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.apply(jwtConfigurer);

        httpSecurity.csrf().disable();

        httpSecurity.httpBasic().disable();

        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
                .authorizeRequests()
                .antMatchers("/security-test/admin/**").hasRole("TEST_ADMIN")
                .antMatchers("/security-test/librarian/**").hasRole("TEST_LIBRARIAN")
                .antMatchers("/security-test/borrower/**").hasRole("TEST_BORROWER");

        httpSecurity
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/librarian/**").hasRole("LIBRARIAN")
                .antMatchers("/borrower/**").hasRole("BORROWER");

        httpSecurity.authorizeRequests()
                .antMatchers(
                        "/*/ping",
                        "/*/port",
                        "/identityprovider/**"
                )
                .permitAll();



        if (h2ConsoleEnabled) {
            httpSecurity.authorizeRequests().antMatchers(h2ConsolePath + "/**").permitAll();

            httpSecurity.csrf().disable();

            httpSecurity.headers().frameOptions().disable();
        }


    }


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
