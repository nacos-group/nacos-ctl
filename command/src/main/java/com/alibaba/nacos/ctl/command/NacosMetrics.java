package com.alibaba.nacos.ctl.command;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_METRICS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_METRICS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.USAGE_METRICS;

/**
 * get metrics data from nacos prometheus http port
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_METRICS, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_METRICS, description = DESCRIPTION_METRICS, subcommands = {
        CommandLine.HelpCommand.class})
@Deprecated
public class NacosMetrics extends NacosCommand {
    
    @CommandLine.Parameters(paramLabel = "<module>", description = "The module name of the metrics")
    public Module module;
    
    @Override
    public String getCommandName() {
        return NAME_METRICS;
    }
    
    @Override
    public void run() {
        
        try {
            Stream<String> metrics = LogicHandler.getMetrics();
            metrics.map(this::mapModule).filter(Objects::nonNull).sorted().forEachOrdered(System.out::println);
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private enum Module {
        /**
         * nacos 客户端模块参数
         */
        CLIENT,
        /**
         * nacos server的参数
         */
        NACOS,
        /**
         * jvm和gc相关信息
         */
        JVM,
        /**
         * 异常信息
         */
        EXCEPTION
    }
    
    /**
     * translate metrics name to chinese.
     *
     * @param s
     * @return
     */
    public String mapModule(String s) {
        
        String backup = s;
        
        Map<String, String> map = enumMap.get(module);
        for (Map.Entry<String, String> e : map.entrySet()) {
            if (s.startsWith(e.getKey())) {
                s = s.replace(e.getKey(), e.getValue());
                break;
            }
        }
        
        if (backup.equals(s)) {
            return null;
        } else {
            return s;
        }
    }
    
    private static Map<String, String> clientMap;
    
    private static Map<String, String> exceptionMap;
    
    private static Map<String, String> nacosMap;
    
    private static Map<String, String> jvmMap;
    
    private static Map<Module, Map<String, String>> enumMap = new HashMap<>();
    
    static {
        jvmMap = new HashMap<>();
        jvmMap.put("system_cpu_usage", "CPU使用率");
        jvmMap.put("system_load_average_1m", "CPU负载");
        jvmMap.put("jvm_memory_used_bytes", "内存使用字节");
        jvmMap.put("jvm_memory_max_bytes", "内存最大字节");
        jvmMap.put("jvm_gc_pause_seconds_count", "GC次数");
        jvmMap.put("jvm_gc_pause_seconds_sum", "GC耗时");
        jvmMap.put("jvm_threads_daemon", "线程数");
        
        clientMap = new HashMap<>();
        clientMap.put("nacos_monitor{name='subServiceCount'}", "订阅的服务数");
        clientMap.put("nacos_monitor{name='pubServiceCount'}", "发布的服务数");
        clientMap.put("nacos_monitor{name='configListenSize'}", "监听的配置数");
        clientMap.put("nacos_client_request_seconds_count", "请求次数");
        clientMap.put("nacos_client_request_seconds_sum", "请求总耗时");
        
        nacosMap = new HashMap<>();
        nacosMap.put("http_server_requests_seconds_count", "http请求次数");
        nacosMap.put("http_server_requests_seconds_sum", "http请求总耗时");
        nacosMap.put("nacos_timer_seconds_sum", "Nacos config水平通知耗时");
        nacosMap.put("nacos_timer_seconds_count", "Nacos config水平通知次数");
        nacosMap.put("nacos_monitor{module=\"config\",name=\"longPolling\",}", "Nacos config长连接数");
        nacosMap.put("nacos_monitor{module=\"config\",name=\"configCount\",}", "Nacos config配置个数");
        nacosMap.put("nacos_monitor{module=\"config\",name=\"dumpTask\",}", "Nacos config配置落盘任务堆积数");
        nacosMap.put("nacos_monitor{module=\"config\",name=\"notifyTask\",}", "Nacos config配置水平通知任务堆积数");
        nacosMap.put("nacos_monitor{module=\"config\",name=\"getConfig\",}", "Nacos config读配置统计数");
        nacosMap.put("nacos_monitor{module=\"config\",name=\"publish\",}", "Nacos config写配置统计数");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"ipCount\",}", "Nacos naming ip个数");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"domCount\",}", "Nacos naming域名个数");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"failedPush\",}", "Nacos naming推送失败数");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"avgPushCost\",}", "Nacos naming平均推送耗时");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"leaderStatus\",}", "Nacos naming角色状态");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"maxPushCost\",}", "Nacos naming最大推送耗时");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"mysqlhealthCheck\",}", "Nacos naming mysql健康检查次数");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"httpHealthCheck\",}", "Nacos naming http健康检查次数");
        nacosMap.put("nacos_monitor{module=\"naming\",name=\"tcpHealthCheck\",}", "Nacos naming tcp健康检查次数");
        
        exceptionMap = new HashMap<>();
        exceptionMap.put("nacos_exception_total{name='db'}", "数据库异常");
        exceptionMap.put("nacos_exception_total{name='configNotify'}", "Nacos config水平通知失败");
        exceptionMap.put("nacos_exception_total{name='unhealth'}", "Nacos config server之间健康检查异常");
        exceptionMap.put("nacos_exception_total{name='disk'}", "Nacos naming写磁盘异常");
        exceptionMap.put("nacos_exception_total{name='leaderSendBeatFailed'}", "Nacos naming leader发送心跳异常");
        exceptionMap.put("nacos_exception_total{name='illegalArgument'}", "请求参数不合法");
        exceptionMap.put("nacos_exception_total{name='nacos'}", "Nacos请求响应内部错误异常");
        
        enumMap.put(Module.NACOS, nacosMap);
        enumMap.put(Module.JVM, jvmMap);
        enumMap.put(Module.EXCEPTION, exceptionMap);
        enumMap.put(Module.CLIENT, clientMap);
    }
}
