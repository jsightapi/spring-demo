package io.jsight.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.File;
import io.jsight.*;
import io.jsight.spring.*;

@Component
@Order(1)
public class JSightFilter implements Filter {

    private final String apiSpecPath = "/app/mock/server.jst"; // MOCK CHANGE
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        JSight.Init(); 
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterchain) 
        throws IOException, ServletException 
    {
        HttpServletResponse           servletResponse = (HttpServletResponse) resp;
        CachedHttpServletRequest      request  = new CachedHttpServletRequest((HttpServletRequest ) req );
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(servletResponse);

        // MOCK CHANGE: skip validation for `GET /_stat` statistic request
        if( "GET".equals(request.getMethod()) && "/_stat".equals(request.getUri())) {
            filterchain.doFilter(request, response);
            response.copyBodyToResponse();
            return;
        }

        // validate request
        ValidationError error = JSight.ValidateHttpRequest(
            this.apiSpecPath, 
            request.getMethod(),
            request.getUri(),
            request.getHeaders(),
            request.getCachedBody()
        );

        if( error != null ) {
            logger.error( "JSight validation error: {}", error.toJSON() );
            servletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            servletResponse.setHeader("Content-Type", "application/json");
            servletResponse.getWriter().write(error.toJSON());
            return;
        }

        // make job
        filterchain.doFilter(request, response);

        // validate response
        error = JSight.ValidateHttpResponse(
            this.apiSpecPath, 
            request.getMethod(),
            request.getUri(),
            response.getStatus(),
            getResponseHeaders(response),
            response.getContentAsByteArray()
        );

        if( error != null ) {
            String errorStr = error.toJSON();
            logger.error( "JSight validation error:\n{}", errorStr );
            servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            servletResponse.setHeader("Content-Type", "application/json");
            servletResponse.setContentLength(errorStr.length());
            servletResponse.getOutputStream().write(errorStr.getBytes());
            return;
            // throw new ServletException(error.toJSON()); // as an option
        }

        response.copyBodyToResponse();
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

    @Override
    public void destroy() {}

    private static String defineSpecAbsolutePath(String resourcePath) {
        String specPath = new File(JSightFilter.class.getClassLoader()
                .getResource(resourcePath)
                .getFile()
            ).getAbsolutePath();
        return specPath;
    }

    private static Map<String, List<String>> getResponseHeaders(HttpServletResponse response) {
        Map<String, List<String>> retHeaders = new HashMap<String, List<String>>();
        for( String h : response.getHeaderNames() ) {
            for( String v : response.getHeaders(h) ) {
                if( ! retHeaders.keySet().contains(h) ) {
                    retHeaders.put(h, new ArrayList<String>());
                }
                retHeaders.get(h).add(v);
            }
        }
        return retHeaders;
    }
}