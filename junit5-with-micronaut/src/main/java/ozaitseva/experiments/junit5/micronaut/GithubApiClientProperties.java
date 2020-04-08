package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("github")
public class GithubApiClientProperties {
    private String token;
}
