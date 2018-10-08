package ma.rest.api.servlet;

import javax.servlet.Servlet;

import org.glassfish.jersey.servlet.ServletContainer;

/**
 * JerseyServletProvider.java
 * 
 * @Date: 24 sept. 2018
 */
public final class JerseyServletProvider
{
    /**
     * Constructeur de la classe JerseyServletProvider.java
     */
    private JerseyServletProvider()
    {
        super();
    }

    /**
     * @param basePackageProvider
     * @param basePackageBinding
     * @return
     */
    public static Servlet getServlet(String basePackageProvider, String basePackageBinding)
    {
        return new ServletContainer(new JerseySpringBeanBindingApplication(basePackageProvider, basePackageBinding));
    }
}
