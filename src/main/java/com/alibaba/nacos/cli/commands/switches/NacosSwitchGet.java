package com.alibaba.nacos.cli.commands.switches;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import java.util.Map;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_GET;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * get the value of a switch
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_GET, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "get switch value", description = "Get the value of a switch")
public class NacosSwitchGet implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<name>", description = "Switch name, input 'all' to get all switches.")
    String grep = "";
    
    @Override
    public void run() {
        
        try {
            Map<String, String> switchMap = LogicHandler.getSwitches();
            
            if ("all".equals(grep)) {
                grep = "";
            }
            switchMap.forEach((k, v) -> {
                if (k.contains(grep)) {
                    System.out.println(k + ":" + v);
                }
            });
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
