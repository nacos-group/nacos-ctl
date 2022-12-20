package com.alibaba.nacos.ctl.command.instance;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_GET;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * get instances
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_GET, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "get instances", description = "Get instances from server.")
public class NacosInstanceGet implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<service name>", description = "Instance service name")
    String serviceName;
    
    @CommandLine.Option(names = {"-s", "--subscribed"}, description = "Subscribed.")
    boolean subscribed = false;
    
    @CommandLine.Option(names = {"-h",
            "--health"}, description = "Healthy or not. Default value is 'true', adding '-h' means 'false'.")
    boolean health = true;
    
    
    @CommandLine.Option(names = {"-c",
            "--cluster"}, paramLabel = "<clusterName>", description = "Cluster name. use -c multi times to set multi value")
    List<String> clusters;
    
    @CommandLine.Option(names = {"-g", "--group"}, paramLabel = "<groupName>", description = "Group name.")
    String groupName;
    
    
    @Override
    public void run() {
        try {
            List<Instance> instances = LogicHandler.getInstances(serviceName, groupName, clusters, health, subscribed);
            System.out.println("Instances:");
            for (Instance in : instances) {
                System.out.println(in);
            }
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
        
    }
}
