package com.example.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by botter on 10/31/16.
 */
@Configuration
@PropertySource(value = {"classpath:dubbo.properties"})
public class DubboConfig {

    @Bean
    @ConfigurationProperties(prefix = "dubbo.application")
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "dubbo.provider")
    public ProviderConfig providerConfig() {
        return new ProviderConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "dubbo.registry")
    public RegistryConfig registryConfig(){
        return new RegistryConfig();
    }

    @Bean
    public ConsumerConfig consumerConfig () {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setRetries(0);
        consumerConfig.setCheck(false);
        return consumerConfig;
    }

    @Bean
    @ConfigurationProperties(prefix = "dubbo.annotation")
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.example");
        return annotationBean;
    }
}
