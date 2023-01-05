package com.alibaba.nacos.ctl.command.spi;

import com.alibaba.nacos.ctl.command.NacosCommand;
import picocli.CommandLine;

import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Nacos command loader.
 *
 * @author xiweng.yy
 */
public class NacosCommandLoader {
    
    private static final NacosCommandLoader INSTANCE = new NacosCommandLoader();
    
    private static final Map<String, NacosCommand> COMMANDS = new ConcurrentHashMap<>();
    
    private static final String IGNORE_INVALID_COMMANDS = "Ignore invalid command(%s) without CommandLine.Command annotation.";
    
    private static final String LOADED_COMMANDS = "Load command(%s) finished.";
    
    private NacosCommandLoader() {
    }
    
    public static NacosCommandLoader getInstance() {
        return INSTANCE;
    }
    
    public void loadCommands() {
        for (NacosCommand each : ServiceLoader.load(NacosCommand.class)) {
            if (notContainCommandAnnotation(each.getClass())) {
                System.out.println(String.format(IGNORE_INVALID_COMMANDS, each.getCommandName()));
                continue;
            }
            System.out.println(String.format(LOADED_COMMANDS, each.getCommandName()));
            COMMANDS.putIfAbsent(each.getCommandName(), each);
        }
    }
    
    private boolean notContainCommandAnnotation(Class<? extends NacosCommand> clazz) {
        return clazz.getAnnotationsByType(CommandLine.Command.class).length == 0;
    }
    
    public Collection<NacosCommand> getLoadedCommands() {
        return COMMANDS.values();
    }
}
