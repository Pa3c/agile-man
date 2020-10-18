package pl.pa3c.agileman;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pl.pa3c.agileman.filter.TokenAuthorizationFIlter;
import pl.pa3c.agileman.security.SecurityConstants;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private TokenAuthorizationFIlter jwtAuthorizationFilter;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
		propsConfig.setLocation(new ClassPathResource("git.properties"));
		propsConfig.setIgnoreResourceNotFound(true);
		propsConfig.setIgnoreUnresolvablePlaceholders(true);
		return propsConfig;
	}

	@ConditionalOnMissingBean(value = BuildProperties.class)
	@Bean
	public BuildProperties buildProperties() throws Exception {
		Properties properties = new Properties();
		properties.put("version", "unavailable");
		return new BuildProperties(properties);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(STATELESS).and()
				.authorizeRequests().antMatchers(SecurityConstants.PUBLIC_URLS).permitAll().anyRequest().authenticated()
				.and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new AgilemanWebMvcConfig();
	}

}
