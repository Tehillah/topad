package ma.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filter to limit concurrent requests. When the number of concurrent requests reaches this limit, return Http Status
 * 429 (too many requests) in the response.
 */
@Component
public class ConcurrentRequestLimitFilter extends GenericFilterBean
{
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ConcurrentRequestLimitFilter.class);

    public static final int HTTP_STATUS_TOO_MANY_REQUEST = 429;

    private static final String FILTER_NAME = "concurrentRequestLimitFilter";

    private long concurrentRequestLimit;

    private String urlPatternsMapping;

    private int count;

    private Object lock = new Object();

    /**
     * Constructeur de la classe ConcurrentRequestLimitFilter.java
     */
    public ConcurrentRequestLimitFilter()
    {
        super(); // Default constructor
    }

    /**
     * The instance
     *
     * @param concurrentRequestLimit :
     */
    ConcurrentRequestLimitFilter(long concurrentRequestLimit, String urlPatternsMapping)
    {
        super();
        this.concurrentRequestLimit = concurrentRequestLimit;
        this.urlPatternsMapping = urlPatternsMapping;
    }

    /**
     * @return
     */
    public String getUrlPatternsMapping()
    {
        return urlPatternsMapping;
    }

    /**
     * @return
     */
    public String getName()
    {
        return FILTER_NAME;
    }

    /**
     * @param request
     * @param response
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException
    {
        try
        {
            boolean ok;
            synchronized (lock)
            {
                ok = count++ < concurrentRequestLimit;
            }
            if (ok)
            {
                // let the request through and process as usual
                chain.doFilter(request, response);
            }
            else
            {
                // handle limit case, e.g. return status code 429 (Too Many Requests)
                // see http://tools.ietf.org/html/rfc6585#page-3
                LOG.info("Number of concurrent request exceeded = " + count);
                ((HttpServletResponse) response).setStatus(HTTP_STATUS_TOO_MANY_REQUEST);
            }
        }
        finally
        {
            synchronized (lock)
            {
                count--;
            }
        }

    }
}
