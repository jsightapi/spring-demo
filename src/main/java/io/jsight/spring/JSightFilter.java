package io.jsight.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.File;
import io.jsight.*;

@Component
@Order(1)
public class JSightFilter implements Filter {

    private final String apiSpecPath = defineSpecAbsolutePath("my-api-spec.jst");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        JSight.Init(); 
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterchain) 
        throws IOException, ServletException 
    {
        HttpServletRequest   request = (HttpServletRequest ) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        System.out.println("=====================");        
        Long start = System.currentTimeMillis();
        String requestPath = request.getRequestURI() 
            + ( (request.getQueryString() == null ) ? "" : ("?" + request.getQueryString()));
 
        ValidationError error = JSight.ValidateHttpRequest(
            this.apiSpecPath, 
            request.getMethod(),
            requestPath,
            null, // Map<String,List<String>> requestHeaders, 
            null  // byte[] requestBody
        );

        if( error != null ) {
            logger.error( "JSight validation error: {}", error.toJSON() );
        }

        filterchain.doFilter(request, response);
        
        logger.info( "response in {}ms",
                System.currentTimeMillis() - start );
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
}