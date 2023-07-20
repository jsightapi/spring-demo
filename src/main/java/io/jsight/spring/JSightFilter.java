package io.jsight.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Order(1)
public class JSightFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) 
        throws IOException, ServletException 
    {
        Long start = System.currentTimeMillis();
        logger.error( "remote host {}" , request.getRemoteHost() );
        System.out.println("I AM FILTER!!!");
        filterchain.doFilter(request, response);
        System.out.println("I AM FILTER!!!");
        logger.error( "response in {}ms",
                System.currentTimeMillis() - start );
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

    @Override
    public void destroy() {}
}