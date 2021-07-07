package com.alibaba.nacos.cli.commands.switches;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_SET;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * set the value of the switch
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_SET, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "set the switch value", description = "Set the switch value.(For some switch in the type of map and list, please use add/remove)")
public class NacosSwitchSet implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<entry>", description = "Switch name")
    String entry;
    
    @CommandLine.Parameters(paramLabel = "<value>", description = "Switch value")
    String value;
    
    @CommandLine.Option(names = {"-d",
            "--debug"}, paramLabel = "<debug>", description = "Input \"-d\" to use debug mode, if affect the local server, true means yes, false means no, default true")
    boolean debug = true;
    
    @Override
    public void run() {
        
        try {
            System.out.println(LogicHandler.updateSwitch(entry, value, debug));
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
