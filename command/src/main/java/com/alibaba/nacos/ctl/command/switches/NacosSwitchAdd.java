package com.alibaba.nacos.ctl.command.switches;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_ADD;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * for some switches in the type of map and list, add an element
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_ADD, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "add an element to a switch", description = "For some switches in the type of map and list, add an element.")
public class NacosSwitchAdd implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<entry>", description = "Switch name")
    String entry;
    
    @CommandLine.Parameters(paramLabel = "<key>", description = "Switch value")
    String key;
    
    @CommandLine.Parameters(paramLabel = "<value>", description = "Switch value")
    String value;
    
    @CommandLine.Option(names = {"-d",
            "--debug"}, paramLabel = "<debug>", description = "Input \"-d\" to use debug mode, if affect the local server, true means yes, false means no, default true")
    boolean debug = true;
    
    @Override
    public void run() {
        
        try {
            LogicHandler.updateSwitchMap(entry, key, value, debug, "add");
            System.out.println("done");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
