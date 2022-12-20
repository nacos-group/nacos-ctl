package com.alibaba.nacos.ctl.command.namespace;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_UPDATE;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * update a namespace
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_UPDATE, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "update a namespace", description = "This API is used to modify a namespace.")
public class NacosNamespaceUpdate implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<name>", description = "Namespace show name.")
    String name;
    
    @CommandLine.Parameters(paramLabel = "<Id>", description = "Namespace Id.")
    String id;
    
    @CommandLine.Parameters(paramLabel = "<description>", description = "Description you gonna update.")
    String description;
    
    @Override
    public void run() {
        try {
            LogicHandler.updateNamespace(name, id, description);
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
