package de.pcmr.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private Environment environment;
    private final String[] allowedOrigins;
    private final String[] allowedMethods;
    private final String[] allowedHeaders;

    @Autowired
    public WebConfig(Environment environment) {
        this.environment = environment;
        allowedOrigins = environment.getProperty("PCMR_CORS_ORIGINS").split(",");
        allowedMethods = environment.getProperty("PCMR_CORS_METHODS").split(",");
        allowedHeaders = environment.getProperty("PCMR_CORS_HEADERS").split(",");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders);
        registry.addMapping("/media/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/media/**")
                .addResourceLocations("file:./media/");
    }
}
