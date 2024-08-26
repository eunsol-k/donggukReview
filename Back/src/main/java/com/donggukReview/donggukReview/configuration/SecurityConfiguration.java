package com.donggukReview.donggukReview.configuration;

import com.donggukReview.donggukReview.security.CustomAuthenticationSuccessHandler;
import com.donggukReview.donggukReview.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(auth) -> auth
						.requestMatchers("/",
						"/login", "/register",
						"/swagger/**", "/swagger-ui/**", "/docs/**",
						"/categories", "/cafeteria/**")
						.permitAll()

						.requestMatchers("/cafeteria")
						.hasRole("ADMIN")

						.anyRequest().authenticated());
		http.formLogin((auth) -> auth.loginProcessingUrl("/login").permitAll().successHandler(successHandler));

		http.csrf(AbstractHttpConfigurer::disable);
		
		http.httpBasic(AbstractHttpConfigurer::disable);

		http.sessionManagement(
				(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.logout((auth) -> auth.logoutUrl("/logout").logoutSuccessUrl("/"));

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}