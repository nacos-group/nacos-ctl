package com.alibaba.nacos.ctl.command;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_CLEAR;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_CLEAR;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.USAGE_CLEAR;

/**
 * clear the terminal, it only works with jline input
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_CLEAR, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_CLEAR, description = DESCRIPTION_CLEAR)
public class NacosClear implements Callable<Integer> {
    
    @Override
    public Integer call() throws Exception {
        return -2;
    }
}
