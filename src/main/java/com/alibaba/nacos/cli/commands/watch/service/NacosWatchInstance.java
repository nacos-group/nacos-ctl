package com.alibaba.nacos.cli.commands.watch.service;

import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * watch instance subcommand
 *
 * @author lehr
 */
@CommandLine.Command(
    name = "instance",
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = USAGE_SWITCH,
    description = DESCRIPTION_SWITCH,
        subcommands = {
                CommandLine.HelpCommand.class,
                NacosWatchInstanceAdd.class,
                NacosWatchInstanceList.class,
                NacosWatchInstanceRemove.class
        })
@Deprecated
public class NacosWatchInstance implements Runnable {


  @CommandLine.Spec CommandLine.Model.CommandSpec spec;

  @Override
  public void run() {
    spec.commandLine().usage(System.err);
  }
}
