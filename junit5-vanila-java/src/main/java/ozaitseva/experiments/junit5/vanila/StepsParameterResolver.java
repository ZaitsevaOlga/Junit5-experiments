package ozaitseva.experiments.junit5.vanila;

import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class StepsParameterResolver implements ParameterResolver {

    private final GithubApiClient githubApiClient;
    private final RepoSteps repoSteps;
    private final ForkSteps forkSteps;
    private final ApiSteps apiSteps;

    StepsParameterResolver() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new AllureOkHttp3())
                        .build())
                .build();
        this.githubApiClient = retrofit.create(GithubApiClient.class);
        this.repoSteps = new RepoSteps(githubApiClient);
        this.forkSteps = new ForkSteps(githubApiClient);
        this.apiSteps = new ApiSteps(githubApiClient);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(RepoSteps.class)
                || parameterContext.getParameter().getType().equals(ForkSteps.class)
                || parameterContext.getParameter().getType().equals(ApiSteps.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (parameterContext.getParameter().getType().equals(RepoSteps.class)) {
            return repoSteps;
        } else if (parameterContext.getParameter().getType().equals(ForkSteps.class)) {
            return forkSteps;
        } else if (parameterContext.getParameter().getType().equals(ApiSteps.class)) {
            return apiSteps;
        } else {
            throw new AssertionError("Unknown parameter found: " + parameterContext.getParameter());
        }
    }
}
