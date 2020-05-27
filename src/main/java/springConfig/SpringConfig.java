package springConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("parser")
@PropertySource("classpath:settings.properties")
public class SpringConfig {

}
