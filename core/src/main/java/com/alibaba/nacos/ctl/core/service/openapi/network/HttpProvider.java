package com.alibaba.nacos.ctl.core.service.openapi.network;

import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.ctl.core.config.ConfigLoader;
import com.alibaba.nacos.ctl.core.config.GlobalConfig;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import com.alibaba.nacos.ctl.core.service.openapi.network.bean.HttpDelete;
import com.alibaba.nacos.ctl.core.utils.AuthUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.DELETE;
import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.GET;
import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.POST;
import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.PUT;
import static org.apache.http.client.config.RequestConfig.custom;

/**
 * @author lehr
 */
public class HttpProvider {
    
    private GlobalConfig config = GlobalConfig.getInstance();
    
    private String authStr = "";
    
    private String serverIdentityKey;
    
    private String serverIdentityValue;
    
    public HttpProvider() {
        String username = config.getUsername();
        String password = config.getPassword();
        try {
            login(username, password);
        } catch (HandlerException e) {
            System.out.println("nacos login failed:" + e.getMessage());
        }
        Properties properties = ConfigLoader.toProperties();
        serverIdentityKey = properties.getProperty("serverIdentityKey", "");
        serverIdentityValue = properties.getProperty("serverIdentityValue", "");
    }
    
    private static final String LOGIN_FAILED = "unknown user!";
    
    private static final String FORBIDDEN = "\"status\":403,\"error\":\"Forbidden\"";
    
    /**
     * http的登录auth
     */
    public void login(String username, String password) throws HandlerException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        String tokenStr = nacosRequest(POST, "/auth/login", params);
        
        // 失败会返回：unknown user!,成功会是一个json串
        if (tokenStr == null) {
            System.out.println("login failed due to network error");
            return;
        }
        if (tokenStr.contains(LOGIN_FAILED)) {
            System.out.println("Login failed, please check your username and password");
            return;
        }
        try {
            JsonObject root = new JsonParser().parse(tokenStr).getAsJsonObject();
            authStr = root.getAsJsonPrimitive("accessToken").getAsString();
        } catch (Exception e) {
            throw new HandlerException("Failed to parse login result, please check nacos-server version.");
        }
    }
    
    private String getNacosUrl() {
        String host = config.getHost();
        Integer port = config.getPort();
        return "http://" + host + ":" + port + "/nacos";
    }
    
    public enum Method {
        /**
         * 分别是http的 get put delete post 方法
         */
        GET,
        PUT,
        DELETE,
        POST
    }
    
    public String prometheusRequest() throws HandlerException {
        String url = getNacosUrl() + "/actuator/prometheus";
        HttpGet httpGet = new HttpGet(url);
        try {
            return sendRequest(httpGet);
        } catch (IOException e) {
            throw new HandlerException("Failed to send request, please check your network.");
        }
    }
    
    /**
     * 发送请求
     *
     * @param method
     * @param path
     * @param parameterMap
     * @return
     */
    public String nacosRequest(Method method, String path, Map<String, Object> parameterMap) throws HandlerException {
        // 拼接完整url
        String url = getNacosUrl() + "/v1" + path;
        List<NameValuePair> params = new ArrayList<>();
        String accessKey = GlobalConfig.getInstance().getAccessKey();
        String secretKey = GlobalConfig.getInstance().getSecretKey();
        Map<String, String> tempStringParameters = new HashMap<>();
        if (parameterMap != null) {
            parameterMap.forEach((key, value) -> tempStringParameters.put(key, String.valueOf(value)));
        }
        Header header = AuthUtils.buildConfigSpas(tempStringParameters, accessKey, secretKey);
        AuthUtils.injectNamingSpas(tempStringParameters, accessKey, secretKey);
        AuthUtils.injectIdentity(header, serverIdentityKey, serverIdentityValue);
        tempStringParameters.forEach((key, value) -> {
            if (value != null) {
                params.add(new BasicNameValuePair(key, value));
            }
        });
        
        // 根据请求类型不同，构造请求，拼接参数，发送请求
        
        try {
            HttpRequestBase req = generateRequest(method, url, params);
            header.getHeader().forEach(req::addHeader);
            String ret = sendRequest(req);
            if (ret != null && ret.contains(FORBIDDEN)) {
                throw new HandlerException("403 Forbidden! Please check your permission.");
            }
            return ret;
        } catch (HandlerException e) {
            throw e;
        } catch (IOException e) {
            throw new HandlerException("Failed to send request, please check your network.");
        } catch (Exception e) {
            throw new HandlerException("Failed to build request.");
        }
    }
    
    private HttpRequestBase generateRequest(Method method, String url, List<NameValuePair> params) throws Exception {
        
        if (GET.equals(method)) {
            params.add(new BasicNameValuePair("accessToken", authStr));
            return new HttpGet(new URIBuilder(url).setParameters(params).build());
        } else if (PUT.equals(method)) {
            HttpPut httpPut = new HttpPut(url + "?accessToken=" + authStr);
            httpPut.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            return httpPut;
        } else if (POST.equals(method)) {
            HttpPost httpPost = new HttpPost(url + "?accessToken=" + authStr);
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            return httpPost;
        } else if (DELETE.equals(method)) {
            com.alibaba.nacos.ctl.core.service.openapi.network.bean.HttpDelete httpDelete = new HttpDelete(
                    url + "?accessToken=" + authStr);
            httpDelete.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            
            return httpDelete;
        } else {
            throw new Exception("illegal method name");
        }
    }
    
    /**
     * 默认的超时设置
     */
    private static final RequestConfig TIMEOUT = custom().setSocketTimeout(1000).setConnectionRequestTimeout(1000)
            .setConnectTimeout(1000).build();
    
    private String sendRequest(HttpRequestBase request) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        
        request.setConfig(TIMEOUT);
        
        CloseableHttpResponse resp = client.execute(request);
        try {
            return EntityUtils.toString(resp.getEntity(), "UTF-8");
        } finally {
            resp.close();
        }
    }
}
