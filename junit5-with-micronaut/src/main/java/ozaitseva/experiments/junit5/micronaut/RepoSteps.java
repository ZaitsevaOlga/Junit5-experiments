package ozaitseva.experiments.junit5.micronaut;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Singleton;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Singleton
@RequiredArgsConstructor
class RepoSteps {

    private final GithubApiClientProperties githubApiClientProperties;
    private final GithubApiClient githubApiClient;

    @Step("When searching for repo {q}")
    RepoSearchResult whenSearchRepo(String q) {
        try {
            Call<RepoSearchResult> request = githubApiClient.searchRepo(githubApiClientProperties.getToken(), q);
            Response<RepoSearchResult> result = request.execute();
            return result.body();
        } catch (IOException e) {
            throw new AssertionError("Unable to search repo");
        }
    }

    @Step("Then repo search response is correct")
    void thenValidateRepoSearchResponse(RepoSearchResult actual, String owner, String name) {
        assertNotNull(actual);
        assertNotNull(actual.getItems());
        assertEquals(1, actual.getItems().size());
        assertEquals(name, actual.getItems().get(0).getName());
        assertEquals(owner, actual.getItems().get(0).getOwner().getLogin());
    }
}
