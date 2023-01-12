package com.alibaba.nacos.ctl.command;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_QUIT;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_QUIT;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_QUIT_ALIAS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.USAGE_QUIT;

/**
 * quit from cli
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_QUIT, aliases = NAME_QUIT_ALIAS, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_QUIT, description = DESCRIPTION_QUIT)
public class NacosQuit implements Callable<Integer> {
    
    @Override
    public Integer call() {
        try {
            LogicHandler.shutdown();
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("bye!");
        return -1;
    }
}
