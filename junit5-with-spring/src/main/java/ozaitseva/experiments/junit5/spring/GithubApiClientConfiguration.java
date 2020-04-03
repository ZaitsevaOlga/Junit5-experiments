package ozaitseva.experiments.junit5.spring;

import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
class GithubApiClientConfiguration {

    @Bean
    Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new AllureOkHttp3())
                        .build())
                .build();
    }

    @Bean
    GithubApiClient githubApiClient(Retrofit retrofit) {
        return retrofit.create(GithubApiClient.class);
    }
}
