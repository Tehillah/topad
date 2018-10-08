package ma.config;

import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ma.rest.api.servlet.JerseyServletProvider;



/**
 * WebAppInitializer.java 
 * 
 * @Date: 27 sept. 2018
 */
@Configuration
@PropertySource("classpath:application.properties")
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    private static final Logger LOG = Logger.getLogger(ConcurrentRequestLimitFilter.class);

    // Concurrent requests limit filter configuration
    private static final long DEFAULT_CONCURRENT_REQUEST_LIMIT = 50;

    private static final String DEFAULT_URL_PATTERN_MAPPING = "/*";

    // jersey servlet configuration
    private static final String REST_JERSEY_SERVLET_NAME = "rest-jersey-servlet";

    private static final String REST_JERSEY_SERVLET_BASE_PACKAGE = "fr.gouv.impots.appli.topad.synchrone.resapi.controllers";

    private static final String REST_JERSEY_SERVLET_BASE_PACKAGE_BINDING = "fr.gouv.impots.appli.topad.synchrone.resapi.controllers";

    private static final String REST_JERSEY_SERVLET_URL_PATTERN = "/restapi/*";

    private static final int REST_JERSEY_SERVLET_STARTUP_ORDER = 2;

    private static final long concurrentRequestLimit;

    private static final String urlPatternsMapping;

    static
    {
        long numberconcurrentRequestLimit = DEFAULT_CONCURRENT_REQUEST_LIMIT;
        String strUrlPatternsMapping = DEFAULT_URL_PATTERN_MAPPING;
        try
        {
            Properties propertiesLoaderUtils = PropertiesLoaderUtils.loadAllProperties("application.properties");
            String strConcurrentRequestLimit = propertiesLoaderUtils.getProperty("concurrentRequestLimit");
            if (!StringUtils.isBlank(strConcurrentRequestLimit))
            {
                numberconcurrentRequestLimit = Long.valueOf(strConcurrentRequestLimit);
            }
            else
            {
                numberconcurrentRequestLimit = DEFAULT_CONCURRENT_REQUEST_LIMIT;
            }
            strUrlPatternsMapping = propertiesLoaderUtils.getProperty("urlPatternsMapping");
            if (StringUtils.isBlank(strUrlPatternsMapping))
            {
                strUrlPatternsMapping = DEFAULT_URL_PATTERN_MAPPING;
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getMessage());
            LOG.info("Error when  loading application.properties. Concurrent limit filter is set with default value : "
                + DEFAULT_CONCURRENT_REQUEST_LIMIT + " concurrent requests max, urlpattern = " + DEFAULT_URL_PATTERN_MAPPING);
            numberconcurrentRequestLimit = DEFAULT_CONCURRENT_REQUEST_LIMIT;
            strUrlPatternsMapping = DEFAULT_URL_PATTERN_MAPPING;
        }
        finally
        {
            concurrentRequestLimit = numberconcurrentRequestLimit;
            urlPatternsMapping = strUrlPatternsMapping;
        }
    }

    /**
     * Add extra Servlets to handle JAX WS and JERSEY Rest API producing
     * 
     * @param servletContext :
     * @throws ServletException :
     */
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        super.onStartup(servletContext);
        // For JaxWS, no need to load WSServlet : each enpoint declared in sun-jawxs cause a new instance of WSServlet.
        // Don't need to add WSServletContextListener neither : WSServletContainerInitializer does it automatically on
        // tomcat startup.

        // Inject Jersey Servlet for RESTFull API
        addExtraServlet(servletContext, REST_JERSEY_SERVLET_NAME,
            JerseyServletProvider.getServlet(REST_JERSEY_SERVLET_BASE_PACKAGE, REST_JERSEY_SERVLET_BASE_PACKAGE_BINDING),
            REST_JERSEY_SERVLET_URL_PATTERN,
            REST_JERSEY_SERVLET_STARTUP_ORDER);

        // Add extrafilter to limit number of concurrent http request.
        ConcurrentRequestLimitFilter concurrentRequestLimitFilter =
            new ConcurrentRequestLimitFilter(concurrentRequestLimit, urlPatternsMapping);
        addConcurrentRequestLimitFilter(servletContext, concurrentRequestLimitFilter, concurrentRequestLimitFilter.getName(),
            concurrentRequestLimitFilter.getUrlPatternsMapping());
    }

    /**
     * Add extra filter.
     * 
     * @see ConcurrentRequestLimitFilter
     * @param servletContext :
     */
    private void addConcurrentRequestLimitFilter(ServletContext servletContext, Filter filter, String filterName, String urlPatternMapping)
    {
        FilterRegistration.Dynamic extraFilter = servletContext.addFilter(filterName, filter);
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR);
        extraFilter.setAsyncSupported(true);
        if (StringUtils.isBlank(urlPatternMapping))
        {
            extraFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        }
        else
        {
            String[] urls = urlPatternMapping.split(",");
            for (String url : urls)
            {
                if (!StringUtils.isBlank(url))
                {
                    extraFilter.addMappingForUrlPatterns(dispatcherTypes, true, url.trim());
                }
            }
        }
    }

    /**
     * Add extra servlet
     * 
     * @param servletContext :
     * @param servletName :
     * @param servlet :
     * @param servletMapping :
     * @param startupOrder :
     */
    private void addExtraServlet(ServletContext servletContext, String servletName, Servlet servlet, String servletMapping,
        int startupOrder)
    {
        // Inject CXF Servlet for SOAP Webservices
        ServletRegistration.Dynamic extraServlet = servletContext.addServlet(servletName, servlet);
        extraServlet.addMapping(servletMapping);
        extraServlet.setAsyncSupported(true);
        extraServlet.setLoadOnStartup(startupOrder);
    }

    /**
     * Spring Dispatcher servlet mapping.
     * 
     * @return : mapping urls
     */
    @Override
    protected String[] getServletMappings()
    {
        return new String[] {"/"};
    }

    /**
     * Spring webapp root config classes.
     * 
     * @return : root config classes
     */
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[] {ApplicationConfig.class};
    }

    /**
     * Spring dispatcher servlet config classes.
     * 
     * @return : servlet config classes
     */
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return null;
    }

    /**
     * Spring dispatcher servlet filters.
     * 
     * @return : servlet filters
     */
    @Override
    protected Filter[] getServletFilters()
    {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
        return new Filter[] {characterEncodingFilter, securityFilterChain};
    }

    /**
     * Spring dispatcher servlet customize registration.
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration)
    {
        registration.setInitParameter("defaultHtmlEscape", "true");
    }

}