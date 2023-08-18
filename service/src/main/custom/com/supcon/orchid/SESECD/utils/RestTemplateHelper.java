//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supcon.orchid.SESECD.utils;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestTemplateHelper {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateHelper.class);
    private static volatile RestTemplate restTemplate;

    public RestTemplateHelper() {
    }

    public static RestTemplate instance(Proxy proxy) {
        if (restTemplate == null) {
            Class var1 = RestTemplateHelper.class;
            synchronized(RestTemplateHelper.class) {
                if (restTemplate == null) {
                    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                    if (proxy != null) {
                        factory.setProxy(proxy);
                    }

                    factory.setConnectTimeout(50000);
                    factory.setReadTimeout(50000);
                    restTemplate = new RestTemplate(factory);
                    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                    restTemplate.getMessageConverters().add(1, new FormHttpMessageConverter());
                    restTemplate.getMessageConverters().add(2, new DataUploadMappingJackson2HttpMessageConverter());
                }
            }
        }

        return restTemplate;
    }

    public static <R> R postInvoke(String url, Proxy proxy, HttpEntity<?> httpEntity, ParameterizedTypeReference<R> responseType) {
        return invoke(url, proxy, HttpMethod.POST, httpEntity, responseType);
    }

    public static <R> R postInvoke(String url, Proxy proxy, HttpEntity<?> httpEntity, Class<R> responseType) {
        return invoke(url, proxy, HttpMethod.POST, httpEntity, responseType);
    }

    public static <R> R invoke(String url, Proxy proxy, HttpMethod method, HttpEntity<?> httpEntity, Class<R> responseType) {
        instance(proxy);
        return restTemplate.exchange(url, method, httpEntity, responseType, new Object[0]).getBody();
    }

    public static <R> R invoke(String url, Proxy proxy, HttpMethod method, HttpEntity<?> httpEntity, ParameterizedTypeReference<R> responseType) {
        instance(proxy);
        return restTemplate.exchange(url, method, httpEntity, responseType, new Object[0]).getBody();
    }

    public static HttpHeaders buildHttpHeaders(Map<String, String> maps) {
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(maps)) {
            maps.keySet().forEach((key) -> {
                headers.add(key, (String)maps.get(key));
            });
        }

        return headers;
    }

    public static MultiValueMap<String, String> generate(Map<String, String> map) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(map)) {
            map.keySet().forEach((key) -> {
                headers.add(key, map.get(key));
            });
        }

        return headers;
    }

    private static class DataUploadMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public DataUploadMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList();
            mediaTypes.add(MediaType.TEXT_HTML);
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.APPLICATION_XML);
            mediaTypes.add(MediaType.APPLICATION_PROBLEM_XML);
            mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
            this.setSupportedMediaTypes(mediaTypes);
        }
    }
}
