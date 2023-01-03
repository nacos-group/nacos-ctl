package com.alibaba.nacos.ctl.command.instance;

import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_INSTANCE;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_INSTANCE;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.USAGE_INSTANCE;

/**
 * instance subcommand
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_INSTANCE, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_INSTANCE, description = DESCRIPTION_INSTANCE, subcommands = {
        CommandLine.HelpCommand.class, NacosInstanceAdd.class, NacosInstanceDelete.class, NacosInstanceUpdate.class,
        NacosInstanceGet.class})
public class NacosInstance implements Runnable {
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
