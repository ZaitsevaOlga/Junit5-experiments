package ozaitseva.experiments.junit5.vanila;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static ozaitseva.experiments.junit5.vanila.StepsParameterResolver.GITHUB_TOKEN;

@RequiredArgsConstructor
class ApiSteps {

    private final GithubApiClient githubApiClient;

    @Step("Given github endpoint available")
    void givenGithubEndpointAvailable() {
        assumeTrue(() -> {
            Call<ResponseBody> request = githubApiClient.smokeCheck(GITHUB_TOKEN);
            try {
                return request.execute().isSuccessful();
            } catch (IOException e) {
                System.out.println("Github API is not available");
                return false;
            }
        }, "Github API is not available");
    }

}
