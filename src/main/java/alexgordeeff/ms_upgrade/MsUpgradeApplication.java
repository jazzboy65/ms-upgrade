package alexgordeeff.ms_upgrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
class MsUpgradeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsUpgradeApplication.class, args);
    }
}
