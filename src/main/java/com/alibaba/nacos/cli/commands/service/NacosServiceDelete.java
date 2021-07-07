package com.alibaba.nacos.cli.commands.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.input.InputGetter;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_DELETE;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * delete a service
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_DELETE, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "delete service", description = "This API is used to remove a service.")
public class NacosServiceDelete implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<serviceName>", description = "Service name.")
    String serviceName;
    
    @CommandLine.Option(names = {"-g", "--groupName"}, paramLabel = "<groupName>", description = "Group name.")
    String groupName;
    
    @Override
    public void run() {
        
        if (InputGetter.cancelConfirm()) {
            return;
        }
        
        try {
            LogicHandler.deleteService(groupName, serviceName);
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
