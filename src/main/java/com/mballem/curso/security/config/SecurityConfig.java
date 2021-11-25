package com.mballem.curso.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mballem.curso.security.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		http.authorizeRequests()
		// acessos públicos liberados
		.antMatchers("/webjars/**", "/css/**", "/image/**","/js/**").permitAll()
		.antMatchers("/", "/home").permitAll()
		.anyRequest().authenticated()
		.and() //Métodos para fazer a autenticação 
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.failureUrl("/login-error")
			.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/");
	
			
	}

	//Método para comparar a senha do banco com a senha do formulário de login
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Criptografia BcryptPasswordEncoder é a recomendada pelo Framework
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

	
	
}
