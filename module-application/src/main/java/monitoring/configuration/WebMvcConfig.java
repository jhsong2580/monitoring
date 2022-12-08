package monitoring.configuration;

import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Cache.Cachecontrol;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public static final String PREFIX_STATIC_RESOURCES = "/img";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // img DIR내 파일 접근시 max-age를 1년으로 설정한다.
        registry.addResourceHandler("/img/**")
            .addResourceLocations("classpath:/static/img/")
            .setCachePeriod(60 * 60 * 24 * 365);

        Cachecontrol cachecontrol = new Cachecontrol();
        cachecontrol.setCachePrivate(true);
        cachecontrol.setNoCache(true);

        registry.addResourceHandler("/img/1.jpeg")
            .addResourceLocations("classpath:/static/img/")
            .setCacheControl(cachecontrol.toHttpCacheControl());
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter etagHeaderFilter = new ShallowEtagHeaderFilter();
        registration.setFilter(etagHeaderFilter);
        registration.addUrlPatterns(PREFIX_STATIC_RESOURCES + "/*");
        return registration;
    }

}
