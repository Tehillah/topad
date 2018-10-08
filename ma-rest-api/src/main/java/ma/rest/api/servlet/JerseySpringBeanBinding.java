package ma.rest.api.servlet;

import java.util.Set;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

/**
 * @author : BKN Due to jersey dependency injection that need to bind services classes (otherwise the injected field is
 *         not populate). Bind all Spring Service components in the basepackage by scanning (otherwise, the bind MUST be
 *         done by hand :-(
 */
public class JerseySpringBeanBinding extends AbstractBinder
{
    private String basePackage;

    /**
     * Constructeur de la classe JerseySpringBeanBinding.java
     *
     * @param basePackage
     */
    JerseySpringBeanBinding(String basePackage)
    {
        this.basePackage = basePackage;
    }

    @Override
    protected void configure()
    {
        // Use Spring scanner all bean candidates in the basepackage
        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider =
            new ClassPathScanningCandidateComponentProvider(true);
        Set<BeanDefinition> beanDefinitions = classPathScanningCandidateComponentProvider.findCandidateComponents(basePackage);
        // bind each bean found
        beanDefinitions.forEach(beanDefinition -> {
            try
            {
                Class implClass = Class.forName(beanDefinition.getBeanClassName());
                Class[] interfaces = implClass.getInterfaces();
                if (interfaces.length > 0)
                {
                    for (Class interf : interfaces)
                    {
                        bind(implClass).to(interf);
                    }
                }
                else
                {
                    bind(implClass).to(implClass).in(Singleton.class);
                }
            }
            catch (ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

}
