package com.alibaba.nacos.ctl.intraction.input.completer;

import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 提供各种命令用到的completer group
 *
 * @author lehr
 */
public class CompleterFactory {
    
    private static final String LOADED_COMPLETER = "LOAD completer(%s) finished.";
    
    /**
     * @author lehr
     */
    @Documented
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Enabled {
    
    
    }
    
    public static List<Completer> loadAll() throws InvocationTargetException, IllegalAccessException {
        List<Completer> completers = new ArrayList();
        Method[] methods = CompleterFactory.class.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Enabled.class)) {
                m.setAccessible(true);
                completers.add((Completer) m.invoke(null));
            }
        }
        
        return completers;
    }
    
    public static Collection<? extends Completer> loadExtensionCompleter() {
        List<Completer> result = new LinkedList<>();
        for (NacosCompleterBuilder each : ServiceLoader.load(NacosCompleterBuilder.class)) {
            result.add(each.build());
            System.out.println(String.format(LOADED_COMPLETER, each.getCompleterName()));
        }
        return result;
    }
    
    /**
     * 对于不提供后续补全的几个基本指令，简单提供String补全组
     *
     * @return
     */
    @Enabled
    public static Completer getBasicGroup() {
        return new StringsCompleter("quit", "clear");
    }
    
    @Enabled
    public static Completer getHelpGroup() {
        Completer help = new StringsCompleter("help");
        Completer helpList = new StringsCompleter("quit", "clear", "config", "use", "watch", "namespace", "instance",
                "switch", "service", "metrics");
        return new ArgumentCompleter(help, helpList, NullCompleter.INSTANCE);
    }
    
    @Enabled
    public static Completer getConfigGroup() {
        Completer config = new StringsCompleter("config");
        Completer configList = new StringsCompleter("add", "list", "delete", "get", "help");
        return new ArgumentCompleter(config, configList, NullCompleter.INSTANCE);
    }
    
    @Enabled
    public static Completer getNamespaceGroup() {
        Completer namespace = new StringsCompleter("namespace");
        Completer namespaceList = new StringsCompleter("add", "delete", "list", "update", "help");
        return new ArgumentCompleter(namespace, namespaceList, NullCompleter.INSTANCE);
    }
    
    @Deprecated
    public static Completer getWatchGroup() {
        Completer watch = new StringsCompleter("watch");
        Completer watchOps = new StringsCompleter("config", "instance");
        Completer watchList = new StringsCompleter("add", "list", "remove");
        return new ArgumentCompleter(watch, watchOps, watchList, NullCompleter.INSTANCE);
    }
    
    @Enabled
    public static Completer getInstanceGroup() {
        Completer instance = new StringsCompleter("instance");
        Completer instanceList = new StringsCompleter("add", "delete", "update", "get", "help");
        return new ArgumentCompleter(instance, instanceList, NullCompleter.INSTANCE);
    }
    
    @Enabled
    public static Completer getSwitchGroup() {
        Completer switches = new StringsCompleter("switch");
        StringsCompleter switchList = new StringsCompleter("get", "set", "add", "remove");
        return new ArgumentCompleter(switches, switchList, new NacosSwitchSetCompleter(), NullCompleter.INSTANCE);
    }
    
    @Enabled
    public static Completer getServiceGroup() {
        Completer service = new StringsCompleter("service");
        Completer serviceList = new StringsCompleter("get", "list", "update", "delete", "add", "help");
        return new ArgumentCompleter(service, serviceList, NullCompleter.INSTANCE);
    }
    
    @Deprecated
    public static Completer getMetricsGroup() {
        Completer metrics = new StringsCompleter("metrics");
        Completer metricsList = new StringsCompleter("jvm", "nacos", "client", "exception");
        return new ArgumentCompleter(metrics, metricsList, NullCompleter.INSTANCE);
    }
    
    @Enabled
    public static Completer getUseGroup() {
        Completer use = new StringsCompleter("use");
        Completer useList = new NacosUseCompleter();
        return new ArgumentCompleter(use, useList, NullCompleter.INSTANCE);
    }
}
