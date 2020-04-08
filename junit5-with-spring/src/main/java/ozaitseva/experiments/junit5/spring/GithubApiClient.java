package ozaitseva.experiments.junit5.spring;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

interface GithubApiClient {

    @GET("/")
    Call<ResponseBody> smokeCheck(@Query(value = "access_token") String token);

    @GET("/search/repositories")
    Call<RepoSearchResult> searchRepo(@Query(value = "access_token") String token,
                                      @Query(value = "q") String query);

    @GET("/repos/{owner}/{repo}/forks")
    Call<List<RepoSearchResult.Repo>> getForks(@Path("owner") String owner,
                                               @Path("repo") String repo,
                                               @Query(value = "access_token") String token);
}
