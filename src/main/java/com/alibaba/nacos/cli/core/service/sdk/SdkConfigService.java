package com.alibaba.nacos.cli.core.service.sdk;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.cli.config.GlobalConfig;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.core.service.sdk.watcher.ConfigWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Access the server by nacos java sdk
 * <p>
 * sdk version is now version 1.4.2, using http to connect to the server.
 *
 * @author lehr
 */
public class SdkConfigService {
    
    private GlobalConfig config = GlobalConfig.getInstance();
    
    private ConfigService configService;
    
    public SdkConfigService() throws HandlerException {
        try {
            configService = NacosFactory.createConfigService(GlobalConfig.getAsProperties());
        } catch (NacosException e) {
            throw new HandlerException("failed to init nacos-client naming sdk", e);
        }
    }
    
    public void shutdown() throws HandlerException {
        try {
            listeners.clear();
            configService.shutDown();
        } catch (NacosException e) {
            throw new HandlerException("Config sdk shutdown failed, forced to stop now", e);
        }
        
    }
    
    public boolean publishConfig(String dataId, String group, String content, String type) throws HandlerException {
        try {
            return configService.publishConfig(dataId, group, content, type);
        } catch (NacosException e) {
            throw new HandlerException("Config sdk publish failed", e);
        }
    }
    
    public boolean removeConfig(String dataId, String group) throws HandlerException {
        try {
            return configService.removeConfig(dataId, group);
        } catch (NacosException e) {
            throw new HandlerException("Config sdk remove failed", e);
        }
    }
    
    public String getConfig(String dataId, String group) throws HandlerException {
        try {
            return configService.getConfig(dataId, group, 2000);
        } catch (NacosException e) {
            throw new HandlerException("Config sdk get failed", e);
        }
    }
    
    @Deprecated
    Executor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> {
        Thread thread = new Thread(r);
        thread.setName("config-sdk-thread");
        return thread;
    });
    
    @Deprecated
    HashMap<String, ConfigWatcher> listeners = new HashMap<>();
    
    @Deprecated
    public List<String> listWatches() {
        return new ArrayList<>(listeners.keySet());
    }
    
    @Deprecated
    public void subscribe(String dataId, String group) {
        try {
            
            String key = dataId + "##" + group;
            if (listeners.get(key) != null) {
                unsubscribe(key);
            }
            ConfigWatcher listener = new ConfigWatcher(executor);
            listeners.put(key, listener);
            
            configService.addListener(dataId, group, listener);
        } catch (NacosException e) {
        
        }
    }
    
    @Deprecated
    public void unsubscribe(String id) {
        
        if (id.contains("##")) {
            String dataId = id.split("##")[0];
            String group = id.split("##")[1];
            configService.removeListener(dataId, group, listeners.get(id));
        }
    }
}
