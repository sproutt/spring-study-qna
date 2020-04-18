
package codesquad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/qna/form").setViewName("/qna/form");
        registry.addViewController("/qna/show").setViewName("/qna/show");
    }

}

