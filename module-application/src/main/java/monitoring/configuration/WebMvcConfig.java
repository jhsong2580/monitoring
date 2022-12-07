package monitoring.configuration;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Cache.Cachecontrol;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // img DIR내 파일 접근시 max-age를 1년으로 설정한다.
        registry.addResourceHandler("/img/**")
            .addResourceLocations("classpath:/static/img/")
            .setCachePeriod(60*60*24*365);

        Cachecontrol cachecontrol = new Cachecontrol();
        cachecontrol.setCachePrivate(true);
        cachecontrol.setNoCache(true);

        registry.addResourceHandler("/img/1.jpeg")
            .addResourceLocations("classpath:/static/img/")
            .setCacheControl(cachecontrol.toHttpCacheControl());
    }
}
