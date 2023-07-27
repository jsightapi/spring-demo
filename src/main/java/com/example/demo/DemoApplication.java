package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "io.jsight"})
public class DemoApplication implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	public static void main(String[] args) {
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            connector.setAttribute("relaxedPathChars", "<>[\\]^`{|}");
            connector.setAttribute("relaxedQueryChars", "<>[\\]^`{|}");
        });
    }
}
