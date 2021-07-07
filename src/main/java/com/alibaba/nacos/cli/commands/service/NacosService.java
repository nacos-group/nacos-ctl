package com.alibaba.nacos.cli.commands.service;

import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_SERVICE;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_SERVICE;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.USAGE_SERVICE;

/**
 * service subcommand
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_SERVICE, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_SERVICE, description = DESCRIPTION_SERVICE, subcommands = {
        CommandLine.HelpCommand.class, NacosServiceAdd.class, NacosServiceGet.class, NacosServiceDelete.class,
        NacosServiceUpdate.class, NacosServiceList.class})
public class NacosService implements Runnable {
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
