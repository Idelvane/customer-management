package io.github.idelvane.customermanagement.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.idelvane.customermanagement.security.filters.JwtAuthenticationEntryPointFilter;
import io.github.idelvane.customermanagement.security.filters.JwtAuthenticationTokenFilter;

/**
 *  Classe que implementa as configurações do Spring Security.
 *  
 * @author idelvane
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationEntryPointFilter unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (activeProfile.equals("test")) {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        } else {
        	http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        	.antMatchers("/customer-management/v1/auth/**", "/customer-management/v1/users/**", "/configuration/security", "/webjars/**", 
        			"/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/manage/**")
    		.permitAll().anyRequest().authenticated();
    		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    		http.headers().cacheControl();
        }
	 }
}
