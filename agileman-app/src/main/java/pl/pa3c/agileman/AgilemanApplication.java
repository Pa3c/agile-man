package pl.pa3c.agileman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableWebMvc
@ComponentScan
public class AgilemanApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgilemanApplication.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer propsConfig
				= new PropertySourcesPlaceholderConfigurer();
		propsConfig.setLocation(new ClassPathResource("git.properties"));
		propsConfig.setIgnoreResourceNotFound(true);
		propsConfig.setIgnoreUnresolvablePlaceholders(true);
		return propsConfig;
	}

	@ConditionalOnMissingBean(value = BuildProperties.class)
	@Bean
	public BuildProperties buildProperties() throws Exception {
		Properties properties = new Properties();
		properties.put("version","unavailable");
		return new BuildProperties(properties);
	}
}
