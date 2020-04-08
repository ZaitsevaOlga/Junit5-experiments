package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.context.annotation.Factory;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Factory
class GithubApiClientConfiguration {

    @Singleton
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new AllureOkHttp3())
                        .build())
                .build();
    }

    @Singleton
    public GithubApiClient githubApiClient(Retrofit retrofit) {
        return retrofit.create(GithubApiClient.class);
    }
}
