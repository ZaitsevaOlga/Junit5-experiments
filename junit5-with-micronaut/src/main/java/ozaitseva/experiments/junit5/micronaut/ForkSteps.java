package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.qameta.allure.Step;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Singleton
@RequiredArgsConstructor
class ForkSteps {
    private final GithubApiClient githubApiClient;

    @Step("When getting forks for repo {owner}/{name}")
    List<RepoSearchResult.Repo> whenGetForks(String owner, String name) {
        try {
            Single<List<RepoSearchResult.Repo>> forksRequest = githubApiClient.getForks(owner, name);
            return forksRequest.blockingGet();
        } catch (HttpClientResponseException e) {
            throw new AssertionError("Unable to get forks");
        }
    }

    @Step("Then forks results are correct")
    void thenValidateForksResponse(List<RepoSearchResult.Repo> forks, String forkOwner) {
        assertNotNull(forks);
        assertEquals(1, forks.size());
        RepoSearchResult.Repo forkedRepo = forks.get(0);
        assertEquals(forkOwner, forkedRepo.getOwner().getLogin());
    }

}
