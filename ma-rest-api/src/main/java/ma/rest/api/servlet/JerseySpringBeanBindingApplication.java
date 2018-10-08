package ma.rest.api.servlet;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * @author : BKN Jersey uses this sort of class to init its ServletContainer.
 */
public class JerseySpringBeanBindingApplication extends ResourceConfig
{
    /**
     * Constructeur de la classe JerseySpringBeanBindingApplication.java
     *
     * @param basePackageProvider
     * @param basePackageBinding
     */
    JerseySpringBeanBindingApplication(String basePackageProvider, String basePackageBinding)
    {
        super();
        // Init the packages containing REST resources i.e. classes annoted with @Path
        packages(basePackageProvider);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(JacksonJsonProvider.class);
    }

}
