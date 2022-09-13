package codesquad;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfigureAdapter implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/list").setViewName("user/list");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/users/login-failed").setViewName("user/login_failed");
        registry.addViewController("/users/profile").setViewName("user/profile");
        registry.addViewController("/questions/form").setViewName("qna/form");
        registry.addViewController("/questions/details").setViewName("qna/show");
    }
}
