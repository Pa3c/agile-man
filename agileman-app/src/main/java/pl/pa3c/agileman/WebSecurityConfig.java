package pl.pa3c.agileman;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Properties;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pl.pa3c.agileman.api.BaseSO;
import pl.pa3c.agileman.api.IdSO;
import pl.pa3c.agileman.api.project.ProjectLabelSO;
import pl.pa3c.agileman.api.state.StateSO;
import pl.pa3c.agileman.api.task.StepSO;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.filter.TokenAuthorizationFilter;
import pl.pa3c.agileman.model.base.BaseLongEntity;
import pl.pa3c.agileman.model.base.LongIdEntity;
import pl.pa3c.agileman.model.base.StringIdEntity;
import pl.pa3c.agileman.model.label.ProjectLabel;
import pl.pa3c.agileman.model.task.Step;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.taskcontainer.State;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.security.SecurityConstants;
import pl.pa3c.agileman.service.UserService;

@Configuration
@EnableWebSecurity
@ComponentScan
@EnableJpaAuditing
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userDetailsService;
	@Autowired
	private TokenAuthorizationFilter jwtAuthorizationFilter;

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
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		
		mapper.typeMap(LongIdEntity.class, IdSO.class).addMapping(LongIdEntity::getId, IdSO::setId);
		mapper.typeMap(StringIdEntity.class, IdSO.class).addMapping(StringIdEntity::getId, IdSO::setId);
		mapper.typeMap(TaskContainer.class, TaskContainerSO.class).addMapping(TaskContainer::getStringType,
				TaskContainerSO::setType);

		mapper.typeMap(State.class, StateSO.class).addMapping(src -> src.getTaskContainer().getId(),
				(dst, value) -> dst.setTaskContainerId((Long) value));
		mapper.typeMap(TaskContainerSO.class, TaskContainer.class).addMapping(TaskContainerSO::getTeamInProjectId,
				(dst, value) -> dst.getTeamInProject().setId((Long) value));
		mapper.typeMap(TaskSO.class, Task.class).addMapping(TaskSO::getTaskContainerId,
				(dst, value) -> dst.getTaskContainer().setId((Long) value));
		mapper.typeMap(StepSO.class, Step.class).addMapping(StepSO::getTaskId,
				(dst, value) -> dst.getTask().setId((Long) value));
		mapper.typeMap(ProjectLabelSO.class, ProjectLabel.class).addMapping(ProjectLabelSO::getProjectId,
				(dst, value) -> dst.getProject().setId((Long) value));

		return mapper;
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
