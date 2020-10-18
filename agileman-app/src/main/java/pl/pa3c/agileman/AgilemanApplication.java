package pl.pa3c.agileman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableWebMvc
@ComponentScan
public class AgilemanApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgilemanApplication.class, args);
	}


}
