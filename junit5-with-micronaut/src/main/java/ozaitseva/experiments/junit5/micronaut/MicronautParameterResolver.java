package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import javax.inject.Named;

class MicronautParameterResolver implements ParameterResolver {

    private ApplicationContext applicationContext;

    MicronautParameterResolver() {
        this.applicationContext = Junit5WithMicronautCommand.context;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return applicationContext != null && applicationContext.containsBean(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Named named = parameterContext.findAnnotation(Named.class).orElse(null);
        if (named != null) {
            return applicationContext.getBean(parameterContext.getParameter().getType(), Qualifiers.byName(named.value()));
        } else {
            return applicationContext.getBean(parameterContext.getParameter().getType());
        }
    }
}
