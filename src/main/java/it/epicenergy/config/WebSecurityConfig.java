package it.epicenergy.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.epicenergy.security.AuthTokenFilter;
import it.epicenergy.service.UserDetailsServiceImpl;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
		.userDetailsService(userDetailsService)
		.passwordEncoder(this.passwordEncoder());
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception { 
		return super.authenticationManager();
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .headers().frameOptions().sameOrigin()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll() // gli endpoint di autenticazione sono pubblicamente accessibili
        .antMatchers("/api/**").authenticated() // le API richiedono l'autenticazione
        .and() 
        .exceptionHandling()
        .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED : " + ex.getMessage()))
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // si abilita una gestione stateless
        .and()
        .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // si applica il filtro prima di processare la richiesta
    }

}
