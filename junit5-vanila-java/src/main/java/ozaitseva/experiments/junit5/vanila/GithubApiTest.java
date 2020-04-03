package ozaitseva.experiments.junit5.vanila;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.List;

@DisplayName("Github Api")
@Epic("Github Api")
@Feature("Repository")
@RequiredArgsConstructor
@ExtendWith(StepsParameterResolver.class)
public class GithubApiTest {

    private final RepoSteps repoSteps;
    private final ForkSteps forkSteps;
    private final ApiSteps apiSteps;

    @Test
    @DisplayName("Searching repo")
    void searchRepo() throws IOException {
        apiSteps.givenGithubEndpointAvailable();
        RepoSearchResult repoSearchResult = repoSteps.whenSearchRepo("gridbdd");
        repoSteps.thenValidateRepoSearchResponse(repoSearchResult, "griddynamics", "GridBDD");
        List<RepoSearchResult.Repo> forks = forkSteps.whenGetForks("griddynamics", "GridBDD");
        forkSteps.thenValidateForksResponse(forks, "ZaitsevaOlga");
    }

    @Test
    @DisplayName("Searching repo uppercase")
    void searchRepoUpperCase() throws IOException {
        apiSteps.givenGithubEndpointAvailable();
        RepoSearchResult repoSearchResult = repoSteps.whenSearchRepo("GRIDBDD");
        repoSteps.thenValidateRepoSearchResponse(repoSearchResult, "griddynamics", "GridBDD");
        List<RepoSearchResult.Repo> forks = forkSteps.whenGetForks("griddynamics", "GridBDD");
        forkSteps.thenValidateForksResponse(forks, "ZaitsevaOlga");
    }
}
