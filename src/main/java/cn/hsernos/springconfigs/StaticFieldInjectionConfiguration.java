package cn.hsernos.springconfigs;

import cn.hsernos.common.utils.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class StaticFieldInjectionConfiguration {

    @Qualifier("messageSource")
    @Autowired
    MessageSource resources;


    @PostConstruct
    private void init() {
        System.out.println("\n\n-----Static Field Injection Configuration----\n\n");
        CheckUtil.setResources(resources);

    }
}