package com.alibaba.nacos.ctl.core.service.sdk.watcher;

import com.alibaba.nacos.api.config.listener.Listener;

import java.util.concurrent.Executor;

/**
 * @author lehr
 */
public class ConfigWatcher implements Listener {
    
    Executor executor;
    
    public ConfigWatcher(Executor executor) {
        this.executor = executor;
    }
    
    @Override
    public void receiveConfigInfo(String configInfo) {
        System.out.println("\n\nconfig update:" + configInfo);
    }
    
    @Override
    public Executor getExecutor() {
        return null;
    }
}
