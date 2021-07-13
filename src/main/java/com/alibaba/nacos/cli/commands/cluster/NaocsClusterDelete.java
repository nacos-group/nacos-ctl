package com.alibaba.nacos.cli.commands.cluster;

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
 * cluster get command handler.
 * @author zzq
 */
@CommandLine.Command(name = NAME_DELETE, sortOptions = SORT_OPTIONS,
        headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING,
        descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING,
        optionListHeading = OPTION_LIST_HEADING, header = "delete cluster node",
        description = "delete cluster node.")
public class NaocsClusterDelete implements Runnable{
    
    @CommandLine.Parameters(paramLabel = "<ip:port>", description = "cluster node metadata.")
    String keyword;
    
    @Override
    public void run() {
        if(InputGetter.cancelConfirm()){
            return ;
        }
        try {
            LogicHandler.deleteClusterNode(keyword);
        } catch (HandlerException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
