package com.supcon.orchid.SESECD.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     http请求客户端
 * </p>
 * @author lufengdong
 * @create 2023-03-22 18:20
 */
@Slf4j
public class HttpInvokeUtils {

    private static volatile RestTemplate restTemplate;

    static {
        if (null == restTemplate) {
            synchronized (HttpInvokeUtils.class) {
                if (restTemplate == null) {
                    restTemplate = RestTemplateFactory.instance();
                    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                    restTemplate.getMessageConverters().add(1, new FormHttpMessageConverter());
                    restTemplate.getMessageConverters().add(2, new DataUploadMappingJackson2HttpMessageConverter());
                }
            }
        }
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, Object> requestBody, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, request, responseType);
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, Class<R> responseType) {
        return invoke(url, method, httpEntity, null, false, responseType);
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, ParameterizedTypeReference<R> reference) {
        return restTemplate.exchange(url, method, httpEntity, reference).getBody();
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, Proxy proxy, Class<R> responseType) {
        return invoke(url, method, httpEntity, proxy, false, responseType);
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, boolean ignoreSsl, Class<R> responseType) {
        return invoke(url, method, httpEntity, null, ignoreSsl, responseType);
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, Proxy proxy, boolean ignoreSsl, Class<R> responseType) {
        return restTemplate.exchange(url, method, httpEntity, responseType).getBody();
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, Class<R> responseType, Map<String, Object> variables) {
        return restTemplate.exchange(url, method, httpEntity, responseType, variables).getBody();
    }

    public static <R> R invoke(String url, HttpMethod method, HttpEntity<?> httpEntity, ParameterizedTypeReference<R> reference, Map<String, Object> variables) {
        return restTemplate.exchange(url, method, httpEntity, reference, variables).getBody();
    }


    private static class DataUploadMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public DataUploadMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_HTML);
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.APPLICATION_XML);
            mediaTypes.add(MediaType.APPLICATION_PROBLEM_XML);
            mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
            super.setSupportedMediaTypes(mediaTypes);
        }
    }

    public static  <T> ResponseEntity<T> post(String baseUrl, String path, Object requestBody, Class<T> responseType) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(path)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
    }

    /**
     * 用于生成RestTemplate的静态工厂
     */
    static class RestTemplateFactory{

        public static RestTemplate instance(){
            SimpleClientHttpRequestFactory factory = getRestTemplateFactory();
            factory.setConnectTimeout(50000);
            factory.setReadTimeout(50000);
            return new RestTemplate(factory);
        }

        private static SimpleClientHttpRequestFactory getRestTemplateFactory(){
            return new SimpleClientHttpRequestFactory();
        }
    }
}
