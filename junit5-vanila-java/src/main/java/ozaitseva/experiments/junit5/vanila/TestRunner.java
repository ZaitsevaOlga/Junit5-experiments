package ozaitseva.experiments.junit5.vanila;

import io.qameta.allure.junitplatform.AllureJunitPlatform;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("ozaitseva.experiments.junit5.vanila")
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
