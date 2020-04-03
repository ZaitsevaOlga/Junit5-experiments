package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.qameta.allure.Step;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Singleton
@RequiredArgsConstructor
class ApiSteps {

    private final GithubApiClient githubApiClient;

    @Step("Given github endpoint available")
    void givenGithubEndpointAvailable() {
        assumeTrue(() -> {
            Single<String> request = githubApiClient.smokeCheck();
            try {
                String result = request.blockingGet();
                System.out.println(result);
                return true;
            } catch (HttpClientResponseException e) {
                return false;
            }
        }, "Github API is not available");
    }

}
