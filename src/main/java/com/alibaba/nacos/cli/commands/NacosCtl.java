package com.alibaba.nacos.cli.commands;

import com.alibaba.nacos.cli.commands.cluster.NacosCluster;
import com.alibaba.nacos.cli.commands.config.NacosConfig;
import com.alibaba.nacos.cli.commands.instance.NacosInstance;
import com.alibaba.nacos.cli.commands.namespace.NacosNamespace;
import com.alibaba.nacos.cli.commands.service.NacosService;
import com.alibaba.nacos.cli.commands.switches.NacosSwitch;
import com.alibaba.nacos.cli.config.ConfigLoader;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * the main command, based on picocli
 *
 * @author lehr
 */
@Command(name = APP_NAME, mixinStandardHelpOptions = MIXIN_STANDARD_HELP_OPTIONS, version = VERSION_NAME, description = CLI_DESCRIPTION, commandListHeading = COMMAND_LIST_HEADING, footer = FOOTER, subcommands = {
        CommandLine.HelpCommand.class, NacosQuit.class, NacosClear.class, NacosConfig.class, NacosNamespace.class,
        NacosInstance.class, NacosSwitch.class, NacosService.class,
        // NacosMetrics.class,
        NacosUse.class, NacosCluster.class
        // NacosWatch.class,
})
public class NacosCtl implements Runnable {
    
    @CommandLine.Option(names = {"-e",
            "--endpoint"}, paramLabel = "<endpoint ip>", description = "The Nacos-Server host Ip.")
    private String host;
    
    @CommandLine.Option(names = {"-p", "--port"}, paramLabel = "<port>", description = "The port of Nacos-Server.")
    private Integer port;
    
    @CommandLine.Option(names = {"-u",
            "--username"}, paramLabel = "<username>", description = "Nacos authentication username.")
    private String username;
    
    @CommandLine.Option(names = {"-pswd",
            "--password"}, paramLabel = "<password>", description = "Nacos password username.")
    private String password;
    
    @CommandLine.Option(names = {"-ak", "--accessKey"}, paramLabel = "<accessKey>", description = "Nacos access key.")
    private String accessKey;
    
    @CommandLine.Option(names = {"-sk", "--secretKey"}, paramLabel = "<secretKey>", description = "Nacos secret key.")
    private String secretKey;
    
    @Override
    public void run() {
        System.out.println(GREETING);
        Map<String, String> confs = new HashMap<>();
        for (Field f : this.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(CommandLine.Option.class)) {
                try {
                    Object o = f.get(this);
                    if (o != null) {
                        confs.put(f.getName(), o.toString());
                    }
                } catch (Exception e) {
                    //todo
                }
            }
        }
        ConfigLoader.preload(confs);
    }
}
