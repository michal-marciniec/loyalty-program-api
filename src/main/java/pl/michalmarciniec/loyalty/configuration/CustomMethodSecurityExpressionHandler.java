package pl.michalmarciniec.loyalty.configuration;

import pl.michalmarciniec.loyalty.domain.entity.PermissionName;
import pl.michalmarciniec.loyalty.domain.entity.RoleName;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class CustomMethodSecurityExpressionHandler extends OAuth2MethodSecurityExpressionHandler {
    private final static Set<String> PACKAGES_TO_REGISTER = new HashSet<>(Arrays.asList(
            RoleName.class.getPackage().getName(),
            PermissionName.class.getPackage().getName()
    ));

    @Override
    public StandardEvaluationContext createEvaluationContextInternal(Authentication auth, MethodInvocation mi) {
        StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(auth, mi);
        StandardTypeLocator typeLocator = (StandardTypeLocator) standardEvaluationContext.getTypeLocator();
        PACKAGES_TO_REGISTER.forEach(typeLocator::registerImport);
        return standardEvaluationContext;
    }
}
