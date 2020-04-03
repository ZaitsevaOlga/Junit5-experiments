package ozaitseva.experiments.junit5.micronaut;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;

@DisplayName("Some simple tests")
@ExtendWith(MicronautParameterResolver.class)
class EchoTest {

    @Test
    @DisplayName("Simple test is working")
    void simpleTestDoingNothing() {
        System.out.println("Just did nothing!");
    }

    @Nested
    @DisplayName("Going deeper")
    class EchoSubTest {

        @DisplayName("Hello world test")
        @ParameterizedTest(name = "{0}, {1}! test")
        @CsvSource({
                "Hello, World",
                "Privet, Dima",
                "Bonjour, Marinette"
        })
        void sayHelloTest(String hello, String name) {
            System.out.println(format("%s, %s!", hello, name));
        }

    }
}
