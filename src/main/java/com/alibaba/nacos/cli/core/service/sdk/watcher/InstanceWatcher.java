package com.alibaba.nacos.cli.core.service.sdk.watcher;

import com.alibaba.nacos.api.naming.listener.AbstractEventListener;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;

import java.util.concurrent.Executor;

/**
 * @author lehr
 */
public class InstanceWatcher extends AbstractEventListener {
    
    Executor executor;
    
    public InstanceWatcher(Executor executor) {
        this.executor = executor;
    }
    
    @Override
    public Executor getExecutor() {
        return executor;
    }
    
    @Override
    public void onEvent(Event event) {
        System.out.println("serviceName: " + ((NamingEvent) event).getServiceName());
        System.out.println("instances from event: " + ((NamingEvent) event).getInstances());
    }
}
