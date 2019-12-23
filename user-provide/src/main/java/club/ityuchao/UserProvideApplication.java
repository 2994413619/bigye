package club.ityuchao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProvideApplication.class, args);
    }

}
