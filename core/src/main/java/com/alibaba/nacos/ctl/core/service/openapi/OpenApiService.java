package com.alibaba.nacos.ctl.core.service.openapi;

import com.alibaba.nacos.api.utils.StringUtils;
import com.alibaba.nacos.ctl.core.config.GlobalConfig;
import com.alibaba.nacos.ctl.core.bean.ConfigVO;
import com.alibaba.nacos.ctl.core.bean.NamespaceVO;
import com.alibaba.nacos.ctl.core.bean.ServiceVO;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.DELETE;
import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.GET;
import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.POST;
import static com.alibaba.nacos.ctl.core.service.openapi.network.HttpProvider.Method.PUT;

/**
 * @author lehr Access the server by nacos http open-api
 */
public class OpenApiService {
    
    private static final String CONF_URL = "/cs/configs";
    
    private static final String NAMESPACE_URL = "/console/namespaces";
    
    private static final String SWITCH_URL = "/ns/operator/switches";
    
    private static final String SERVICE_URL = "/ns/service";
    
    private static final String INSTANCE_URL = "/ns/instance";
    
    private GlobalConfig config = GlobalConfig.getInstance();
    
    private HttpProvider httpProvider = new HttpProvider();
    
    private boolean debugOn = false;
    
    public String updateSwitch(String entry, String value, Boolean debug) throws HandlerException {
        if (debug) {
            debugOn = true;
        } else {
            if (debugOn) {
                return "Warning! You just modified a switch in debug mode, please set it back and restart nacosctl!";
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("entry", entry);
        params.put("value", value);
        params.put("debug", debug);
        return httpProvider.nacosRequest(PUT, SWITCH_URL, params);
    }
    
    public List<ConfigVO> listConfigs(String dataId, String group, Integer pageNo, Integer pageSize, String search)
            throws HandlerException {
        
        Map<String, Object> params = new HashMap<>();
        params.put("dataId", dataId);
        params.put("group", group);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("search", search);
        if (!StringUtils.isEmpty(config.getNamespaceId())) {
            params.put("tenant", config.getNamespaceId());
        }
        String ret = httpProvider.nacosRequest(GET, CONF_URL, params);
        
        JsonObject data = new JsonParser().parse(ret).getAsJsonObject();
        List<ConfigVO> results = new ArrayList<>();
        data.get("pageItems").getAsJsonArray().forEach(json -> {
            String configData = json.getAsJsonObject().get("dataId").getAsString();
            String configGroup = json.getAsJsonObject().get("group").getAsString();
            results.add(new ConfigVO(configData, configGroup));
        });
        return results;
    }
    
    public Map<String, String> getSwitches() throws HandlerException {
        String ret = httpProvider.nacosRequest(GET, SWITCH_URL, null);
        JsonObject data = new JsonParser().parse(ret).getAsJsonObject();
        Map<String, String> map = new HashMap<>();
        data.entrySet().forEach(e -> {
            map.put(e.getKey(), e.getValue().toString());
        });
        return map;
    }
    
    public String addService(String groupName, String serviceName, Float protectThreshold, String metadata,
            String selector) throws HandlerException {
        
        // fixme 这个selector是什么json类型字符串，这里先直接写String了
        
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", groupName);
        params.put("serviceName", serviceName);
        params.put("namespaceId", config.getNamespaceId());
        params.put("protectThreshold", protectThreshold);
        params.put("metadata", metadata);
        params.put("selector", selector);
        return httpProvider.nacosRequest(POST, SERVICE_URL, params);
    }
    
    public String updateService(String groupName, String serviceName, Float protectThreshold, String metadata,
            String selector) throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", groupName);
        params.put("serviceName", serviceName);
        params.put("namespaceId", config.getNamespaceId());
        params.put("protectThreshold", protectThreshold);
        params.put("metadata", metadata);
        params.put("selector", selector);
        return httpProvider.nacosRequest(POST, SERVICE_URL, params);
        
    }
    
    public List<ServiceVO> listService(String serviceName, String groupName, Integer pageNo, Integer pageSize)
            throws HandlerException {
        
        Map<String, Object> params = new HashMap<>();
        params.put("hasIpCount", false);
        params.put("withInstances", false);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("serviceNameParam", serviceName);
        params.put("groupNameParam", groupName);
        params.put("namespaceId", config.getNamespaceId());
        
        String ret = httpProvider.nacosRequest(GET, "/ns/catalog/services", params);
        
        JsonObject data = new JsonParser().parse(ret).getAsJsonObject();
        List<ServiceVO> results = new ArrayList<>();
        data.get("serviceList").getAsJsonArray().forEach(json -> {
            String name = json.getAsJsonObject().get("name").getAsString();
            String group = json.getAsJsonObject().get("groupName").getAsString();
            String healthCount = json.getAsJsonObject().get("healthyInstanceCount").getAsString();
            results.add(new ServiceVO(name, group, healthCount));
        });
        return results;
    }
    
    public String deleteService(String groupName, String serviceName) throws HandlerException {
        
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", groupName);
        params.put("serviceName", serviceName);
        params.put("namespaceId", config.getNamespaceId());
        return httpProvider.nacosRequest(DELETE, SERVICE_URL, params);
        
    }
    
    public String getService(String groupName, String serviceName) throws HandlerException {
        
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", groupName);
        params.put("serviceName", serviceName);
        params.put("namespaceId", config.getNamespaceId());
        return httpProvider.nacosRequest(GET, SERVICE_URL, params);
        
    }
    
    public List<NamespaceVO> listNamespaces() throws HandlerException {
        String ret = httpProvider.nacosRequest(GET, NAMESPACE_URL, null);
        // 解析json把namespace对象里的name和id提取出来
        JsonObject root = new JsonParser().parse(ret).getAsJsonObject();
        JsonArray data = root.getAsJsonArray("data");
        List<NamespaceVO> list = new ArrayList<>();
        
        data.forEach(n -> {
            JsonObject jo = n.getAsJsonObject();
            String namespaceId = jo.get("namespace").getAsString();
            String namespace = jo.get("namespaceShowName").getAsString();
            list.add(new NamespaceVO(namespace, namespaceId));
        });
        return list;
    }
    
    public String addNamespace(String namespaceName, String customNamespaceId, String namespaceDesc)
            throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        params.put("namespaceName", namespaceName);
        params.put("customNamespaceId", customNamespaceId);
        params.put("namespaceDesc", namespaceDesc);
        return httpProvider.nacosRequest(POST, NAMESPACE_URL, params);
    }
    
    public String updateNamespace(String namespaceName, String customNamespaceId, String namespaceDesc)
            throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        params.put("namespaceShowName", namespaceName);
        params.put("namespace", customNamespaceId);
        params.put("namespaceDesc", namespaceDesc);
        return httpProvider.nacosRequest(PUT, NAMESPACE_URL, params);
    }
    
    public String deleteNamespace(String namespaceId) throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        params.put("namespaceId", namespaceId);
        return httpProvider.nacosRequest(DELETE, NAMESPACE_URL, params);
    }
    
    public String addInstance(String ip, Integer port, Double weight, Boolean enabled, Boolean health, String metadata,
            String clusterName, String serviceName, String groupName, Boolean ephemeral) throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        params.put("port", port);
        params.put("namespaceId", config.getNamespaceId());
        params.put("weight", weight);
        params.put("enabled", enabled);
        params.put("health", health);
        params.put("metadata", metadata);
        params.put("clusterName", clusterName);
        params.put("serviceName", serviceName);
        params.put("groupName", groupName);
        params.put("ephemeral", ephemeral);
        return httpProvider.nacosRequest(POST, INSTANCE_URL, params);
    }
    
    public String updateInstance(String ip, Integer port, Double weight, Boolean enabled, String metadata,
            String clusterName, String serviceName, String groupName, Boolean ephemeral) throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        params.put("port", port);
        
        params.put("namespaceId", config.getNamespaceId());
        
        params.put("weight", weight);
        params.put("enabled", enabled);
        params.put("metadata", metadata);
        
        params.put("clusterName", clusterName);
        
        params.put("serviceName", serviceName);
        params.put("groupName", groupName);
        
        params.put("ephemeral", ephemeral);
        return httpProvider.nacosRequest(PUT, INSTANCE_URL, params);
        
    }
    
    public String deleteInstance(String ip, Integer port, String clusterName, String serviceName, String groupName,
            Boolean ephemeral) throws HandlerException {
        Map<String, Object> params = new HashMap<>();
        
        params.put("serviceName", serviceName);
        params.put("groupName", groupName);
        params.put("ip", ip);
        params.put("port", port);
        params.put("clusterName", clusterName);
        params.put("namespaceId", config.getNamespaceId());
        params.put("ephemeral", ephemeral);
        return httpProvider.nacosRequest(DELETE, INSTANCE_URL, params);
    }
    
    /**
     * 普罗米修斯metrics的数据页面 直接grep掉#开头的，以stream返回便于上层函数做流处理
     *
     * @return
     */
    public Stream<String> getMetrics() throws HandlerException {
        String ret = httpProvider.prometheusRequest();
        String[] lines = ret.split("\n");
        return Arrays.stream(lines).filter(s -> !s.startsWith("#"));
    }
}
