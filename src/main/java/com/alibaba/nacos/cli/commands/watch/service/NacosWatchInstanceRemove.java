package com.alibaba.nacos.cli.commands.watch.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * remove a watcher
 *
 * @author lehr
 */
@CommandLine.Command(name = "remove", sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "remove watcher", description = "Remove an instance watcher.")
@Deprecated
public class NacosWatchInstanceRemove implements Runnable {
    
    
    @CommandLine.Parameters(paramLabel = "<service name>", description = "Instance service name")
    String serviceName;
    
    @CommandLine.Parameters(paramLabel = "<group name>", description = "Instance group name")
    String groupName;
    
    @CommandLine.Option(names = {"-c", "--cluster"}, paramLabel = "<clusterName>", description = "Cluster name.")
    List<String> clusters;
    
    @Override
    public void run() {
        
        System.out.println(LogicHandler.unwatchService(serviceName, groupName, clusters));
        
    }
}
