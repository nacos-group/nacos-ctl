package com.alibaba.nacos.ctl.command.utils;

/**
 * @author lehr 用来统一记录各种指令的提示信息的，方便编辑和浏览所以写一起了
 */
public class HintUtils {
    
    /**
     * 主命令的各种提示信息
     */
    public static final String APP_NAME = "nacosctl";
    
    public static final String VERSION_NAME = "v1.0.1";
    
    public static final String CLI_DESCRIPTION = "NacosCtl is a fast, scalable, helpful client that can let you connect to nacos-server easily.";
    
    public static final String COMMAND_LIST_HEADING = "%nCommands:%n%nThe most commonly used commands are:%n";
    
    public static final String FOOTER = "%nSee 'NacosCtl help <command>' to read about a specific subcommand or concept.";
    
    public static final boolean MIXIN_STANDARD_HELP_OPTIONS = true;
    
    public static final String GREETING = "  /* [NacosCtl]" + "\n   * Version: " + VERSION_NAME + "\n   * Hey！"
            + "\n   * NacosCtl is a fast, scalable, helpful command line tool. "
            + "\n   * that can let you connect to nacos-server easily." + "\n   */";
    
    
    /**
     * 一些控制子命令的提示样式的默认设置
     */
    public static final boolean SORT_OPTIONS = false;
    
    public static final String HEADER_HEADING = "@|bold,underline Usage:|@%n%n";
    
    public static final String SYNOPSIS_HEADING = "%n";
    
    public static final String DESCRIPTION_HEADING = "%n@|bold,underline Description:|@%n%n";
    
    public static final String PARAMETER_LIST_HEADING = "%n@|bold,underline Parameters:|@%n";
    
    public static final String OPTION_LIST_HEADING = "%n@|bold,underline Options:|@%n";
    
    /**
     * 子命令的名称&用法&说明
     */
    public static final String NAME_CLEAR = "clear";
    
    public static final String USAGE_CLEAR = "Clear the terminal";
    
    public static final String DESCRIPTION_CLEAR = "Remove everything on your screen. Only JLine-Input support this function";
    
    public static final String NAME_QUIT = "quit";
    
    public static final String NAME_QUIT_ALIAS = "stop";
    
    public static final String USAGE_QUIT = "Quit from the Cli";
    
    public static final String DESCRIPTION_QUIT = "Quit from the cli, and close the connection with current server.";
    
    public static final String NAME_CONFIG = "config";
    
    public static final String USAGE_CONFIG = "nacos config";
    
    public static final String DESCRIPTION_CONFIG = "nacos config module";
    
    public static final String NAME_INSTANCE = "instance";
    
    public static final String USAGE_INSTANCE = "nacos instance";
    
    public static final String DESCRIPTION_INSTANCE = "Instance crud.";
    
    public static final String NAME_NAMESPACE = "namespace";
    
    public static final String USAGE_NAMESPACE = "nacos namespace";
    
    public static final String DESCRIPTION_NAMESPACE = "Namespace crud.";
    
    public static final String NAME_SERVICE = "service";
    
    public static final String USAGE_SERVICE = "nacos service";
    
    public static final String DESCRIPTION_SERVICE = "Service crud.";
    
    public static final String NAME_SWITCH = "switch";
    
    public static final String USAGE_SWITCH = "nacos switch";
    
    public static final String DESCRIPTION_SWITCH = "System switch subcommand.";
    
    public static final String NAME_METRICS = "metrics";
    
    public static final String USAGE_METRICS = "get nacos metrics from prometheus http port";
    
    public static final String DESCRIPTION_METRICS = "Nacos 0.8.0 improves the monitoring system, "
            + "supporting Nacos operation status monitoring through exposing metrics data "
            + "access to third-party monitoring system."
            + " Currently, prometheus, elastic search and influxdb are supported. "
            + "The docs introduce how prometheus and grafana monitor Nacos."
            + " Here is Nacos grafana monitoring page. "
            + "You can find out for yourself how to use elastic search and influxdb.";
    
    public static final String NAME_ADD = "add";
    
    public static final String NAME_IMPORT = "import";
    
    public static final String NAME_EXPORT = "export";
    
    public static final String NAME_DELETE = "delete";
    
    public static final String NAME_UPDATE = "update";
    
    public static final String NAME_SET = "set";
    
    public static final String NAME_GET = "get";
    
    public static final String NAME_LIST = "list";
    
    public static final String NAME_USE = "use";
    
    public static final String USAGE_USE = "switch to different namespace";
    
    public static final String DESCRIPTION_USE = "like mysql use db, you can choice default namespace so then you don't need to set namespace anymore when you send request.";
}
