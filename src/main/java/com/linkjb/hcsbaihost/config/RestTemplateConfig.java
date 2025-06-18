package com.linkjb.hcsbaihost.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;

/**
 * @ClassName RestTemplateConfig
 * @Description restTemplate入参
 * @Author shark
 * @Data 2023/8/23 17:14
 **/
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .connectTimeout(Duration.ofSeconds(60))
                .connectTimeout(Duration.ofSeconds(60))
                .build();
        List<HttpMessageConverter<?>> msgConv = restTemplate.getMessageConverters();
        if (msgConv != null && !msgConv.isEmpty()) {
            for (int i = 0; i < msgConv.size(); i++) {
                if (msgConv.get(i).getClass().equals(StringHttpMessageConverter.class)) {
                    msgConv.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                }
            }
        }

        return restTemplate;
    }
}
