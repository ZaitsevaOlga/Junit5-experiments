package ozaitseva.experiments.junit5.micronaut;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;

import javax.inject.Singleton;
import java.io.IOException;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Singleton
@RequiredArgsConstructor
class ApiSteps {

    private final GithubApiClientProperties githubApiClientProperties;
    private final GithubApiClient githubApiClient;

    @Step("Given github endpoint available")
    void givenGithubEndpointAvailable() {
        assumeTrue(() -> {
            Call<ResponseBody> request = githubApiClient.smokeCheck(githubApiClientProperties.getToken());
            try {
                return request.execute().isSuccessful();
            } catch (IOException e) {
                System.out.println("Github API is not available");
                return false;
            }
        }, "Github API is not available");
    }

}
