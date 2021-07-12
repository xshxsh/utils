package com.example.utils.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: shihai.xie
 * @create: 2021-06-15 15:22
 * @description: RestTemplate配置类
 **/

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Value("${kf5.email_address}")
    private String emailAddress;
    @Value("${kf5.password}")
    private String password;

    private static final ThreadLocal<String> ORIGINAL_RESPONSE = new ThreadLocal<>();

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(1000*10*5);
        clientHttpRequestFactory.setReadTimeout(1000*10*5);
        //调用接口填充用户密码进行授权
        final RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(emailAddress, password).build();
        //在内存中缓冲所有传出和传入的流,使用此包装器可以多次读取 ClientHttpResponse getBody()
        final BufferingClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
        restTemplate.setRequestFactory(factory);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        restTemplate.getInterceptors().add(new RestTemplateLogRecordInterceptor());
        return restTemplate;
    }

    /**
     * 无论调第三方接口是否正常，都不抛出异常，只记录日志
     */
    static class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            //啥也不做，就不会抛出异常
        }
    }

    /**
     * 记录客户端HTTP请求和响应内容
     */
    static class RestTemplateLogRecordInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            log.debug("调用RC接口开始，请求地址：{}，请求方法：{}，请求头：{}，请求内容：{}",
                    request.getURI(), request.getMethod(), request.getHeaders(), new String(body,
                            StandardCharsets.UTF_8));
            ClientHttpResponse response = execution.execute(request, body);
            String responseStr = new String(FileCopyUtils.copyToByteArray(response.getBody()));
            log.debug("调用RC接口结束，响应码：{}，响应消息：{}，响应头：{}，响应内容：{}",
                    response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), responseStr);
            ORIGINAL_RESPONSE.set("调用 KF5 API 发生异常,Code：{" + response.getRawStatusCode() + "}," + "Message：" + responseStr);
            return response;
        }
    }

    /**
     * 请求内容：自动从Redis查询token放到请求头中
     */
    public static <T> HttpEntity<T> httpEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity(JSONObject.toJSONString(body), headers);
    }

    /**
     * 检查响应是否是正常返回的
     */
    public static <T> T checkResp(ResponseEntity<T> response) {
        if (ResponseEnum.isSuccessful(response.getStatusCodeValue())) {
            return response.getBody();
        }
        final String msg = ORIGINAL_RESPONSE.get();
        ORIGINAL_RESPONSE.remove();
        throw new BizException(msg);
    }

}