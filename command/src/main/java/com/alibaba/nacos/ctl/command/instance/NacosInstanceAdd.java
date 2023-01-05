package com.alibaba.nacos.ctl.command.instance;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_ADD;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * add an instance
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_ADD, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "add an instance.", description = "Register an instance to service.")
public class NacosInstanceAdd implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<ip>", description = "IP of instance.")
    String ip;
    
    @CommandLine.Parameters(paramLabel = "<port>", description = "Port of instance.")
    Integer port;
    
    @CommandLine.Parameters(paramLabel = "<service name>", description = "Instance service name")
    String serviceName;
    
    @CommandLine.Option(names = {"-w", "--weight"}, paramLabel = "<weight>", description = "Weight")
    Double weight;
    
    @CommandLine.Option(names = {"-e", "--enabled"}, description = "Not Enable.")
    boolean enabled = true;
    
    @CommandLine.Option(names = {"-h",
            "--health"}, description = "Healthy or not. Default value is 'true', adding '-h' means 'false'.")
    boolean health = true;
    
    @CommandLine.Option(names = {"-m", "--metadata"}, paramLabel = "<metadata>", description = "Extended information.")
    String metadata;
    
    @CommandLine.Option(names = {"-c", "--cluster"}, paramLabel = "<clusterName>", description = "Cluster name.")
    String clusterName;
    
    @CommandLine.Option(names = {"-g", "--group"}, paramLabel = "<groupName>", description = "Group name.")
    String groupName;
    
    @CommandLine.Option(names = {"-p",
            "--ephemeral"}, paramLabel = "<boolean>", description = "If instance is ephemeral.")
    boolean ephemeral = true;
    
    
    @Override
    public void run() {
        try {
            LogicHandler.addInstance(ip, port, weight, enabled, health, metadata, clusterName, serviceName, groupName,
                    ephemeral);
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
