package com.alibaba.nacos.cli.commands.namespace;

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
 * create a namespace
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_ADD, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "create namespace", description = "This API is used to create a namespace in Nacos.")
public class NacosNamespaceAdd implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<name>", description = "Namespace show name.")
    String name;
    
    @CommandLine.Parameters(paramLabel = "<Id>", description = "Namespace Id.")
    String id;
    
    @CommandLine.Option(names = {"-d",
            "--description"}, paramLabel = "<description>", description = "Description of the namespace.")
    String description;
    
    
    @Override
    public void run() {
        try {
            LogicHandler.addNamespace(name, id, description);
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
