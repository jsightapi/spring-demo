package io.jsight.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.lang.Runtime;
import io.jsight.*;
import io.jsight.spring.*;
import java.text.NumberFormat;

@Component
@Order(2)
public class JSightMock implements Filter {

    private final String mockPath = "/app/mock/";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        JSight.Init(); 
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterchain) 
        throws IOException, ServletException 
    {
        HttpServletRequest  request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String responseBody = "";

        if( "GET".equals(request.getMethod()) && "/_stat".equals(request.getRequestURI())) {
            responseBody = String.format("%s\n## Java\n\n%s", JSight.Stat(), memInfo());
        }

        if( "POST".equals(request.getMethod()) && "/check-jdoc-exchange-200k".equals(request.getRequestURI())) {
            responseBody = Files.readString(Paths.get(mockPath + "test-responses/jdoc-exchange-response-200k.json"), StandardCharsets.UTF_8);
        }
        
        if( "POST".equals(request.getMethod()) && "/check-jdoc-exchange-1m".equals(request.getRequestURI())) {
            responseBody = Files.readString(Paths.get(mockPath + "test-responses/jdoc-exchange-response-1m.json"), StandardCharsets.UTF_8);
        }

        if( "PUT".equals(request.getMethod()) && "/dogs/123".equals(request.getRequestURI())) {
            responseBody = Files.readString(Paths.get(mockPath + "test-responses/dog-response-500b.json"), StandardCharsets.UTF_8);
        }

        if( "POST".equals(request.getMethod()) && "/cats".equals(request.getRequestURI())) {
            responseBody = Files.readString(Paths.get(mockPath + "test-responses/cat-response-1k.json"), StandardCharsets.UTF_8);
        }
        
        if( "GET".equals(request.getMethod()) && "/hello-world".equals(request.getRequestURI())) {
            responseBody = "\"Hello, World!\"";
        }

        response.getOutputStream().write(responseBody.getBytes("UTF-8"));
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

    @Override
    public void destroy() {}

    public String memInfo() {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        sb.append("Free memory: ");
        sb.append(format.format(freeMemory / 1024));
        sb.append(" Kb\n");
        sb.append("Allocated memory: ");
        sb.append(format.format(allocatedMemory / 1024));
        sb.append(" Kb\n");
        sb.append("Max memory: ");
        sb.append(format.format(maxMemory / 1024));
        sb.append(" Kb\n");
        sb.append("Total free memory: ");
        sb.append(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
        sb.append(" Kb\n");
        return sb.toString();
    }    

    static void initHttpHeaders(HttpServletResponse response, String rawHeaders)
    {
        for ( String rawHeader : rawHeaders.split("\n") ) {
            String[] h = rawHeader.split(":", 2);
            if (h.length == 2) {
                response.addHeader(h[0], h[1].trim());
            }
        }
    }
}