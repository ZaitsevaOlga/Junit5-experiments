package ozaitseva.experiments.junit5.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "github")
public class GithubApiClientProperties {
    private String token;
}
