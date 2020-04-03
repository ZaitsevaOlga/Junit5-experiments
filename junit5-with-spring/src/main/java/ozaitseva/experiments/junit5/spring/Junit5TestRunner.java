package ozaitseva.experiments.junit5.spring;

import io.qameta.allure.junitplatform.AllureJunitPlatform;
import lombok.RequiredArgsConstructor;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@Component
@RequiredArgsConstructor
class Junit5TestRunner implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    @Override
    public void run(String... args) {
        TestApplication.context = applicationContext;
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        /**
                         *  Specific package is not working in case of jar running, due to Spring repackaging of jar.
                         *  Used default packageName (empty string) to find any tests in project.
                         **/
                        selectPackage("")
                )
                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        AllureJunitPlatform allureListener = new AllureJunitPlatform();
        TestExecutionListener[] testExecutionListeners = new TestExecutionListener[]{allureListener, listener};
        launcher.registerTestExecutionListeners(testExecutionListeners);

        launcher.execute(request);
    }
}
