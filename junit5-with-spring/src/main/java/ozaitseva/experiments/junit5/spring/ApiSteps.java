package ozaitseva.experiments.junit5.spring;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Component
@RequiredArgsConstructor
class ApiSteps {

    private final GithubApiClient githubApiService;

    @Step("Given github endpoint available")
    void givenGithubEndpointAvailable() {
        assumeTrue(() -> {
            Call<ResponseBody> request = githubApiService.smokeCheck();
            try {
                return request.execute().isSuccessful();
            } catch (IOException e) {
                return false;
            }
        }, "Github API is not available");
    }

}
