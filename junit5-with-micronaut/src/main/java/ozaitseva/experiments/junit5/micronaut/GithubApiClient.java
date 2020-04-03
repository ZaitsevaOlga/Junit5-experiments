package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

import java.util.List;

@Client("https://api.github.com/")
@Header(name = "User-Agent", value = "test")
public interface GithubApiClient {
    @Get("/")
    Single<String> smokeCheck();

    @Get("/search/repositories")
    Single<RepoSearchResult> searchRepo(@QueryValue(value = "q") String query);

    @Get("/repos/{owner}/{repo}/forks")
    Single<List<RepoSearchResult.Repo>> getForks(@PathVariable String owner, @PathVariable String repo);
}
