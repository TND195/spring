package com.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
            .authorizeRequests();
        for(String url: ignoreUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        // options method allow permission
        registry.antMatchers(HttpMethod.OPTIONS)
            .permitAll();

            registry
                .antMatchers("/").hasRole("MEMBER")
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403");
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }
}
