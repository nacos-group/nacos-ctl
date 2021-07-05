package com.alibaba.nacos.cli.commands.namespace;

import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * namespace subcommand
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_NAMESPACE,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = USAGE_NAMESPACE,
    description = DESCRIPTION_NAMESPACE,
    subcommands = {
      CommandLine.HelpCommand.class,
      NacosNamespaceAdd.class,
      NacosNamespaceList.class,
      NacosNamespaceDelete.class,
      NacosNamespaceUpdate.class,
    })
public class NacosNamespace implements Runnable {

    @CommandLine.Spec CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
