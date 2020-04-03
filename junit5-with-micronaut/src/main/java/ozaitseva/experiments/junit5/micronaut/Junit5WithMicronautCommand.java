package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.qameta.allure.junitplatform.AllureJunitPlatform;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import picocli.CommandLine.Command;

import javax.inject.Inject;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@Command(name = "junit-5-with-micronaut", description = "...",
        mixinStandardHelpOptions = true)
public class Junit5WithMicronautCommand implements Runnable {

    public static ApplicationContext context;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Junit5WithMicronautCommand.class, args);
    }

    @Inject
    public void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public void run() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("ozaitseva.experiments.junit5.micronaut")
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
