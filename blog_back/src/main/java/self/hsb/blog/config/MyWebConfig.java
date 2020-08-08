package self.hsb.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SipooHe
 * @date 2020/4/18 15:23
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Value("${image.path}")
    private String path;

    /**
     * 图片资源 url 处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/hsblog/images/upload/**")
                .addResourceLocations("file:"+path);
    }
}
