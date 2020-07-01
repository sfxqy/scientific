package cn.hsernos.springconfigs;

import cn.hsernos.intercept.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");

        registry.addInterceptor(new NeedFourInterceptor())
                .addPathPatterns("/v4/**");

        registry.addInterceptor(new NeedInterceptor())
                .addPathPatterns("/need/**")
                .excludePathPatterns("/need/login", "/need/register", "/need/findPassword");

        registry.addInterceptor(new RootInterceptor())
                .addPathPatterns("/root/**")
                .excludePathPatterns("/root/login");

        registry.addInterceptor(new SkillInterceptor())
                .addPathPatterns("/skill/**")
                .excludePathPatterns("/skill/login");

        registry.addInterceptor(new SkillThreeInterceptor())
                .addPathPatterns("/v3/**");
    }
}