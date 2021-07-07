package com.alibaba.nacos.cli.commands.switches;

import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_SWITCH;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_SWITCH;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.USAGE_SWITCH;

/**
 * switch subcommand
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_SWITCH, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_SWITCH, description = DESCRIPTION_SWITCH, subcommands = {
        CommandLine.HelpCommand.class, NacosSwitchGet.class, NacosSwitchSet.class, NacosSwitchAdd.class,
        NacosSwitchRemove.class})
public class NacosSwitch implements Runnable {
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
