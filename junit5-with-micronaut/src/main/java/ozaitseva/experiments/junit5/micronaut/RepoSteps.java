package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.qameta.allure.Step;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Singleton
@RequiredArgsConstructor
class RepoSteps {
    private final GithubApiClient githubApiClient;

    @Step("When searching for repo {q}")
    RepoSearchResult whenSearchRepo(String q) {
        try {
            Single<RepoSearchResult> request = githubApiClient.searchRepo(q);
            return request.blockingGet();
        } catch (HttpClientResponseException e) {
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
