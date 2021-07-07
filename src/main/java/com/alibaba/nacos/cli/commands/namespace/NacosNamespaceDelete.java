package com.alibaba.nacos.cli.commands.namespace;

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
 * delete a namespace
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_DELETE, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "delete namespace", description = "This API is used to remove a namespace in Nacos.")
public class NacosNamespaceDelete implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<Id>", description = "Namespace Id.")
    String id;
    
    
    @Override
    public void run() {
        
        if (InputGetter.cancelConfirm()) {
            return;
        }
        
        try {
            LogicHandler.deleteNamespace(id);
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
