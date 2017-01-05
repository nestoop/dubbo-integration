package cn.nest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by perk
 *
 * @DATE 2017/1/4
 * @DESC
 */
public class CustomerHttpMessageConverterConfig implements HttpMessageConverter {

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<MediaType> mediaTypes = Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);

    @Override
    public boolean canRead(Class aClass, MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }

        for (MediaType supportMediaType : mediaTypes) {
            if (supportMediaType.includes(mediaType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canWrite(Class aClass, MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }

        for (MediaType supportMediaType : mediaTypes) {
            if (supportMediaType.includes(mediaType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return mediaTypes;
    }

    @Override
    public Object read(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        System.out.println("read.........");
        if (httpInputMessage.getHeaders().getContentType().equals(MediaType.APPLICATION_XML)) {
            return null;
        } else if (httpInputMessage.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON)) {
            return objectMapper.readValue(httpInputMessage.getBody(), aClass);
        }
        return null;
    }

    @Override
    public void write(Object o, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        objectMapper.writeValue(httpOutputMessage.getBody(), o);
    }
}
