package ozaitseva.experiments.junit5.spring;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Parameter;

class SpringParameterResolver implements ParameterResolver {

    private ApplicationContext applicationContext;

    SpringParameterResolver() {
        this.applicationContext = TestApplication.context;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return applicationContext != null && applicationContext.containsBean(parameter.getName());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        int index = parameterContext.getIndex();
        Class<?> testClass = extensionContext.getRequiredTestClass();
        return ParameterResolutionDelegate.resolveDependency(parameter, index, testClass,
                applicationContext.getAutowireCapableBeanFactory());
    }
}
