package com.example.ecobookclient.controller;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;

@Configuration
public class SessionConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addInitializers(servletContext -> servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE)));
    }
}

