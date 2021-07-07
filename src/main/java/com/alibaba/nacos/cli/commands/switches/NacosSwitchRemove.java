package com.alibaba.nacos.cli.commands.switches;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.input.InputGetter;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * for some switches in the type of map and list, remove an element
 *
 * @author lehr
 */
@CommandLine.Command(name = "remove", sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "remove an element to a switch", description = "For some switches in the type of map and list, remove an element.")
public class NacosSwitchRemove implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<entry>", description = "Switch name")
    String entry;
    
    @CommandLine.Parameters(paramLabel = "<key>", description = "Switch value")
    String key;
    
    @CommandLine.Option(names = {"-d",
            "--debug"}, paramLabel = "<debug>", description = "Input \"-d\" to use debug mode, if affect the local server, true means yes, false means no, default true")
    boolean debug = true;
    
    @Override
    public void run() {
        
        if (InputGetter.cancelConfirm()) {
            return;
        }
        
        try {
            System.out.println(LogicHandler.updateSwitchMap(entry, key, null, debug, "remove"));
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
