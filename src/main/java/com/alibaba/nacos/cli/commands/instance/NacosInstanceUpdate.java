package com.alibaba.nacos.cli.commands.instance;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_UPDATE;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * update an instance
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_UPDATE, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "modify instance", description = "Modify an instance of service.")
public class NacosInstanceUpdate implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<ip>", description = "IP of instance.")
    String ip;
    
    @CommandLine.Parameters(paramLabel = "<port>", description = "Port of instance.")
    Integer port;
    
    @CommandLine.Parameters(paramLabel = "<service name>", description = "Instance service name")
    String serviceName;
    
    @CommandLine.Option(names = {"-w", "--weight"}, paramLabel = "<weight>", description = "Weight")
    Double weight;
    
    @CommandLine.Option(names = {"-e", "--enabled"}, description = "Enabled or not.")
    boolean enabled = true;
    
    @CommandLine.Option(names = {"-m", "--metadata"}, paramLabel = "<metadata>", description = "Extended information.")
    String metadata;
    
    @CommandLine.Option(names = {"-c", "--cluster"}, paramLabel = "<clusterName>", description = "Cluster name.")
    String clusterName;
    
    @CommandLine.Option(names = {"-g", "--group"}, paramLabel = "<groupName>", description = "Group name.")
    String groupName;
    
    @CommandLine.Option(names = {"-p", "--ephemeral"}, description = "If instance is ephemeral.")
    boolean ephemeral = true;
    
    @Override
    public void run() {
        try {
            LogicHandler.updateInstance(ip, port, weight, enabled, metadata, clusterName, serviceName, groupName,
                    ephemeral);
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
