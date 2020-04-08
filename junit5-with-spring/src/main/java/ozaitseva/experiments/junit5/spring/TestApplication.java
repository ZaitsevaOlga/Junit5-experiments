package ozaitseva.experiments.junit5.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(GithubApiClientProperties.class)
public class TestApplication {

    static ApplicationContext context;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        SpringApplication.run(TestApplication.class, args);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Execution time: " + totalTime);
    }
}
