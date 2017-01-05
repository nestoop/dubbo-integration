package cn.nest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Botter
 *
 * @DATE 2017/1/4
 * @DESC
 */
@Configuration
public class ConverterConfig extends WebMvcConfigurerAdapter {

    @Bean
    HttpMessageConverter customerHttpMessageConverter() {
        return new CustomerHttpMessageConverterConfig();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter() {
            @Override
            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                ObjectMapper objectMapper = new ObjectMapper();
                String valueAsStringJSON = objectMapper.writeValueAsString(object);
                String afterJSONString = valueAsStringJSON + "加密";
                outputMessage.getBody().write(afterJSONString.getBytes());
            }
        };
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customerHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
    }
}
