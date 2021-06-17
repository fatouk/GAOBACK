package com.example.gao.security;



import com.example.gao.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

        
        @Autowired
        UtilisateurRepository utilisateurRepository;
        @Bean
        public CorsFilter corsFilter() {
        System.out.println("cors filter============");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//		
//	}
@Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/css/**",
                        "/**.css",
                        "/**.svg",
                        "/svg/**",
                        "/**.js",
                        "/**.**",
                        "/**.ico",
                        "/assets/**",
                        "/js/**",
                        "/fonts/**",
                        "/resources/**",
                        "/libs/**");
        web.ignoring()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**");

        return;
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable().csrf().disable()
		//don't create session
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
			.antMatchers(SecurityConstants.WHITE_URL)
			.permitAll()
			.anyRequest().authenticated()
		.and()
			.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
 

        
}
