package com.test.security.configurations;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.security.configurations.security.AuthorizationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint entryPoint;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthorizationFilter authFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/swagger-ui.html", "/configuration/**", "/swagger-resources/**", "/v2/api-docs/**")
				.permitAll().antMatchers("/public/**", "/topic/**", "/webSockConnect/**").permitAll()
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(entryPoint).and().logout()
				.logoutSuccessHandler(logoutSuccessHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();

		http.addFilterBefore(corsFilter(), ChannelProcessingFilter.class);
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/springfox-swagger-ui/**").and().ignoring().antMatchers("/configuration/ui")
				.and().ignoring().antMatchers("/swagger-resources").and().ignoring().antMatchers("/v2/api-docs").and()
				.ignoring().antMatchers("/public/**").and().ignoring().antMatchers("/webSockConnect/**").and()
				.ignoring().antMatchers("/topic/**").and().ignoring().antMatchers("/auth").and().ignoring();
	}

	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public Filter corsFilter() {
		return new Filter() {
			
			@Override
			public void init(FilterConfig filterConfig) throws ServletException {}
			
			@Override
			public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
					throws IOException, ServletException {
				
				HttpServletResponse response = (HttpServletResponse) res;
				HttpServletRequest request = (HttpServletRequest) req;
				
				response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
                response.setHeader("Access-Control-Allow-Headers",
                        "X-Requested-With, Content-Type, Authorization, Origin, Accept," +
                                " Access-Control-Request-Method, Access-Control-Request-Headers");

				if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
                    response.getWriter().println("Success");
                } else {
					chain.doFilter(request, response);
                }
				
			}
			
			@Override
			public void destroy() {}
		};
	}

}
