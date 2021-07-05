package com.alibaba.nacos.cli.commands.watch.config;

import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * watch config subcommand
 *
 * @author lehr
 */
@CommandLine.Command(
    name = "config",
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = "watch config",
    description = "watch config",
    subcommands = {
      CommandLine.HelpCommand.class,
      NacosWatchConfigAdd.class,
      NacosWatchConfigList.class,
      NacosWatchConfigRemove.class
    })
@Deprecated
public class NacosWatchConfig implements Runnable {

    @CommandLine.Spec CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
