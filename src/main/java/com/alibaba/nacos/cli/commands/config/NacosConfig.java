package com.alibaba.nacos.cli.commands.config;

import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_CONFIG;
import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_CONFIG;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.USAGE_CONFIG;

/**
 * config subcommand
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_CONFIG, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = USAGE_CONFIG, description = DESCRIPTION_CONFIG, subcommands = {
        CommandLine.HelpCommand.class, NacosConfigAdd.class, NacosConfigDelete.class, NacosConfigGet.class,
        //      ConfigImport.class,
        //      ConfigExport.class,
        NacosConfigList.class})
public class NacosConfig implements Runnable {
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
