package com.alibaba.nacos.ctl.core;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.ctl.core.bean.ConfigVO;
import com.alibaba.nacos.ctl.core.bean.NamespaceVO;
import com.alibaba.nacos.ctl.core.bean.ServiceVO;
import com.alibaba.nacos.ctl.core.config.GlobalConfig;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import com.alibaba.nacos.ctl.core.service.openapi.OpenApiService;
import com.alibaba.nacos.ctl.core.service.sdk.SdkConfigService;
import com.alibaba.nacos.ctl.core.service.sdk.SdkNamingService;
import com.alibaba.nacos.ctl.core.utils.SwitchUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Do the real logic callings from commands.
 *
 * @author lehr
 */
public class LogicHandler {
    
    private static OpenApiService openApiService;
    
    private static SdkConfigService sdkConfigService;
    
    private static SdkNamingService sdkNamingService;
    
    private static GlobalConfig config;
    
    
    public static void init() throws HandlerException {
        config = GlobalConfig.getInstance();
        openApiService = new OpenApiService();
        
        sdkConfigService = new SdkConfigService();
        sdkNamingService = new SdkNamingService();
    }
    
    public static void refresh() {
        try {
            sdkConfigService.shutdown();
            sdkNamingService.shutdown();
            config = GlobalConfig.getInstance();
            openApiService = new OpenApiService();
            sdkConfigService = new SdkConfigService();
            sdkNamingService = new SdkNamingService();
        } catch (Exception e) {
            System.out.println("refresh Logic Handler failed.");
            e.printStackTrace();
        }
    }
    
    /**
     * 切换当前的默认Namespace ，读取到新到Namespace和Id后，需要：
     *
     * <p>1.对于OpenApiService，由于每次发送Http请求会直接拿Namespace，所以无所谓，改了全局配置文件就好了
     *
     * <p>2.对于sdk服务，由于每次创建新service会写死namespace，所以需要重新加载，缺点也是会导致耗时等待比较长
     *
     * @param namespace
     * @return
     */
    public static void useNamespace(String namespace) throws HandlerException {
        try {
            String[] split = namespace.split("\\(");
            String name = split[0];
            String id = split[1].substring(0, split[1].length() - 1);
            config.setGlobalNamespace(name, id);
            sdkNamingService.shutdown();
            sdkConfigService.shutdown();
            sdkNamingService = new SdkNamingService();
            sdkConfigService = new SdkConfigService();
        } catch (Exception e) {
            throw new HandlerException("Illegal input.");
        }
    }
    
    public static void shutdown() throws HandlerException {
        sdkConfigService.shutdown();
        sdkNamingService.shutdown();
    }
    
    public static String getConfig(String group, String dataId) throws HandlerException {
        return sdkConfigService.getConfig(dataId, group);
    }
    
    public static void postConfig(String group, String dataId, String content, String type) throws HandlerException {
        sdkConfigService.publishConfig(dataId, group, content, type);
    }
    
    public static List<ConfigVO> listConfigs(String dataId, String group, Integer pageNo, Integer pageSize,
            String search)
            throws HandlerException {
        return openApiService.listConfigs(dataId, group, pageNo, pageSize, search);
    }
    
    public static void deleteConfig(String group, String dataId) throws HandlerException {
        sdkConfigService.removeConfig(dataId, group);
    }
    
    public static String updateSwitch(String entry, String value, Boolean debug) throws HandlerException {
        String[] pair = SwitchUtils.translate(entry, value);
        return openApiService.updateSwitch(pair[0], pair[1], debug);
    }
    
    public static String updateSwitchMap(String entry, String key, String value, Boolean debug, String mode)
            throws HandlerException {
        String[] pair = SwitchUtils.translateMap(entry, key, value, mode);
        return openApiService.updateSwitch(pair[0], pair[1], debug);
    }
    
    public static Map<String, String> getSwitches() throws HandlerException {
        return openApiService.getSwitches();
    }
    
    public static String getSwitchValueByName(String name) throws HandlerException {
        Map<String, String> switches = openApiService.getSwitches();
        return switches.get(name);
    }
    
    public static String addService(String groupName, String serviceName, Float protectThreshold, String metadata,
            String selector) throws HandlerException {
        return openApiService.addService(groupName, serviceName, protectThreshold, metadata, selector);
    }
    
    public static String updateService(String groupName, String serviceName, Float protectThreshold, String metadata,
            String selector) throws HandlerException {
        return openApiService.updateService(groupName, serviceName, protectThreshold, metadata, selector);
    }
    
    public static String deleteService(String groupName, String serviceName) throws HandlerException {
        return openApiService.deleteService(groupName, serviceName);
    }
    
    public static String getService(String groupName, String serviceName) throws HandlerException {
        return openApiService.getService(groupName, serviceName);
    }
    
    public static List<ServiceVO> listServices(String serviceName, String group, Integer pageNo, Integer pageSize)
            throws HandlerException {
        return openApiService.listService(serviceName, group, pageNo, pageSize);
    }
    
    public static List<NamespaceVO> listNamespaces() throws HandlerException {
        return openApiService.listNamespaces();
    }
    
    public static String addNamespace(String namespaceName, String customNamespaceId, String namespaceDesc)
            throws HandlerException {
        return openApiService.addNamespace(namespaceName, customNamespaceId, namespaceDesc);
    }
    
    public static String updateNamespace(String namespaceName, String customNamespaceId, String namespaceDesc)
            throws HandlerException {
        return openApiService.updateNamespace(namespaceName, customNamespaceId, namespaceDesc);
    }
    
    public static String deleteNamespace(String namespaceId) throws HandlerException {
        return openApiService.deleteNamespace(namespaceId);
    }
    
    public static void addInstance(String ip, Integer port, Double weight, Boolean enabled, Boolean health,
            String metadata, String clusterName, String serviceName, String groupName, Boolean ephemeral)
            throws HandlerException {
        sdkNamingService.addInstance(ip, port, weight, enabled, health, metadata, clusterName, serviceName, groupName,
                ephemeral);
    }
    
    public static String updateInstance(String ip, Integer port, Double weight, Boolean enabled, String metadata,
            String clusterName, String serviceName, String groupName, Boolean ephemeral) throws HandlerException {
        return openApiService
                .updateInstance(ip, port, weight, enabled, metadata, clusterName, serviceName, groupName, ephemeral);
    }
    
    public static void deleteInstance(String ip, Integer port, String clusterName, String serviceName, String groupName,
            Boolean ephemeral) throws HandlerException {
        sdkNamingService.deleteInstance(ip, port, clusterName, serviceName, groupName, ephemeral);
    }
    
    public static List<Instance> getInstances(String serviceName, String groupName, List<String> clusters,
            boolean healthy, boolean subscribed) throws HandlerException {
        return sdkNamingService.selectInstances(serviceName, groupName, clusters, healthy, subscribed);
    }
    
    @Deprecated
    public static Stream<String> getMetrics() throws HandlerException {
        return openApiService.getMetrics();
    }
    
    @Deprecated
    public static String watchService(String serviceName, String group, List<String> clusters) {
        sdkNamingService.subscribe(serviceName, group, clusters);
        return "done";
    }
    
    @Deprecated
    public static String unwatchService(String serviceName, String group, List<String> clusters) {
        sdkNamingService.unsubscribe(serviceName, group, clusters);
        return "done";
    }
    
    @Deprecated
    public static List<String> listServiceWatches() {
        return sdkNamingService.listWatches();
    }
    
    @Deprecated
    public static String watchConfig(String dataId, String group) {
        sdkConfigService.subscribe(dataId, group);
        return "done";
    }
    
    @Deprecated
    public static List<String> listConfigWatches() {
        return sdkConfigService.listWatches();
    }
    
    @Deprecated
    public static String unwatchConfig(String id) {
        sdkConfigService.unsubscribe(id);
        return "done";
    }
}
