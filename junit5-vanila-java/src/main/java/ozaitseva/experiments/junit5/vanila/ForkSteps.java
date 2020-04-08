package ozaitseva.experiments.junit5.vanila;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ozaitseva.experiments.junit5.vanila.StepsParameterResolver.GITHUB_TOKEN;

@RequiredArgsConstructor
class ForkSteps {
    private final GithubApiClient githubApiClient;

    @Step("When getting forks for repo {owner}/{name}")
    List<RepoSearchResult.Repo> whenGetForks(String owner, String name) {
        try {
            Call<List<RepoSearchResult.Repo>> forksRequest = githubApiClient.getForks(owner, name,
                    GITHUB_TOKEN);
            Response<List<RepoSearchResult.Repo>> forksResponse = forksRequest.execute();
            return forksResponse.body();
        } catch (IOException e) {
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
