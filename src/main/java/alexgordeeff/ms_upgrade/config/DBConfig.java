package alexgordeeff.ms_upgrade.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "alexgordeeff.ms_upgrade.repository")
public class DBConfig {
}
