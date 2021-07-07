package com.alibaba.nacos.cli.commands.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_ADD;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * add a service
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_ADD, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "add a service", description = "This API is used to create a new empty service.")
public class NacosServiceAdd implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<serviceName>", description = "Service name.")
    String serviceName;
    
    @CommandLine.Option(names = {"-s",
            "--selector"}, paramLabel = "<selector>", description = "Visit strategy, please input JSON string.")
    String selector;
    
    @CommandLine.Option(names = {"-g", "--groupName"}, paramLabel = "<groupName>", description = "Group name.")
    String groupName;
    
    @CommandLine.Option(names = {"-t",
            "--threshold"}, paramLabel = "<threshold>", description = "Set value from 0 to 1, default 0.")
    float threshold;
    
    @CommandLine.Option(names = {"-m", "--metadata"}, paramLabel = "<metadata>", description = "Metadata of service.")
    String metadata;
    
    @Override
    public void run() {
        
        try {
            System.out.println(LogicHandler.addService(groupName, serviceName, threshold, metadata, selector));
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
