package com.alibaba.nacos.cli.core.service.sdk;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.cli.config.GlobalConfig;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.core.service.sdk.watcher.InstanceWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Access the server by nacos java sdk
 *
 * <p>sdk version is now version 1.4.2, using http to connect to the server.
 *
 * @author lehr
 */
public class SdkNamingService {
    
    private NamingService namingService;
    
    private GlobalConfig config = GlobalConfig.getInstance();
    
    public SdkNamingService() throws HandlerException {
        try {
            namingService = NacosFactory.createNamingService(GlobalConfig.getAsProperties());
        } catch (NacosException e) {
            throw new HandlerException("failed to init nacos-client naming sdk", e);
        }
    }
    
    public void shutdown() throws HandlerException {
        try {
            listeners.clear();
            namingService.shutDown();
        } catch (NacosException e) {
            throw new HandlerException("Naming sdk shutdown failed, forced to stop now", e);
        }
    }
    
    public List<Instance> selectInstances(String serviceName, String groupName, List<String> clusters, boolean healthy,
            boolean subscribed) throws HandlerException {
        try {
            if (groupName == null) {
                return namingService.selectInstances(serviceName, clusters, healthy, subscribed);
            } else {
                return namingService.selectInstances(serviceName, groupName, clusters, healthy, subscribed);
            }
        } catch (NacosException e) {
            throw new HandlerException("Naming sdk failed to select instance", e);
        }
    }
    
    public void deleteInstance(String ip, Integer port, String clusterName, String serviceName, String groupName,
            Boolean ephemeral) throws HandlerException {
        try {
            Instance instance = makeInstance(ip, port, null, null, null, null, clusterName, ephemeral);
            if (groupName == null) {
                namingService.deregisterInstance(serviceName, instance);
            } else {
                namingService.deregisterInstance(serviceName, groupName, instance);
            }
        } catch (NacosException e) {
            throw new HandlerException("Naming sdk failed to delete instance", e);
        }
    }
    
    public void addInstance(String ip, Integer port, Double weight, Boolean enabled, Boolean health, String metadata,
            String clusterName, String serviceName, String groupName, Boolean ephemeral) throws HandlerException {
        try {
            Instance instance = makeInstance(ip, port, weight, enabled, health, metadata, clusterName, ephemeral);
            
            if (groupName == null) {
                namingService.registerInstance(serviceName, instance);
            } else {
                namingService.registerInstance(serviceName, groupName, instance);
            }
        } catch (NacosException e) {
            throw new HandlerException("Naming sdk failed to add new instance", e);
        }
    }
    
    /**
     * 封装的一个创建带有指定参数的实例的方法
     *
     * @param ip
     * @param port
     * @param weight
     * @param enabled
     * @param health
     * @param metadata
     * @param clusterName
     * @param ephemeral
     * @return
     */
    private Instance makeInstance(String ip, Integer port, Double weight, Boolean enabled, Boolean health,
            String metadata, String clusterName, Boolean ephemeral) {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        
        if (weight != null) {
            instance.setWeight(weight);
        }
        if (metadata != null) {
            // todo 这个metadata的设置，输入格式
            // instance.setMetadata();
        }
        if (health != null) {
            instance.setHealthy(health);
        }
        if (ephemeral != null) {
            instance.setEphemeral(ephemeral);
        }
        if (enabled != null) {
            instance.setEnabled(enabled);
        }
        if (clusterName != null) {
            instance.setClusterName(clusterName);
        }
        return instance;
    }
    
    
    @Deprecated
    public List<String> listWatches() {
        return new ArrayList<>(listeners.keySet());
    }
    
    @Deprecated
    Executor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> {
        Thread thread = new Thread(r);
        thread.setName("naming-sdk-thread");
        return thread;
    });
    
    InstanceWatcher listener = new InstanceWatcher(executor);
    
    // todo service的监听设置写完,关于多参数多修复
    
    @Deprecated
    public void subscribe(String serviceName, String group, List<String> clusters) {
        try {
            String key = getKey(serviceName, group, clusters);
            
            if (clusters == null) {
                namingService.subscribe(serviceName, group, listener);
            }
            namingService.subscribe(serviceName, group, clusters, listener);
        } catch (NacosException e) {
        
        }
    }
    
    @Deprecated
    HashMap<String, InstanceWatcher> listeners = new HashMap<>();
    
    @Deprecated
    private String getKey(String serviceName, String group, List<String> clusters) {
        StringBuffer sb = new StringBuffer();
        sb.append(serviceName).append("##").append(group).append("##");
        if (clusters != null) {
            for (String c : clusters) {
                sb.append(c).append(",");
            }
        }
        return sb.toString();
    }
    
    @Deprecated
    public void unsubscribe(String serviceName, String group, List<String> clusters) {
        try {
            String key = getKey(serviceName, group, clusters);
            namingService.unsubscribe(serviceName, listener);
            // namingService.unsubscribe(serviceName,group,clusters,listener);
        } catch (NacosException e) {
        
        }
    }
}
