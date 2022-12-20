package com.alibaba.nacos.ctl.command.watch.service;

import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_SWITCH;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.USAGE_SWITCH;

/**
 * watch instance subcommand
 *
 * @author lehr
 */
@CommandLine.Command(name = "instance", sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_SWITCH, description = DESCRIPTION_SWITCH, subcommands = {
        CommandLine.HelpCommand.class, NacosWatchInstanceAdd.class, NacosWatchInstanceList.class,
        NacosWatchInstanceRemove.class})
@Deprecated
public class NacosWatchInstance implements Runnable {
    
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
