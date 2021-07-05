package com.alibaba.nacos.cli.commands.watch.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * add a listener to instance
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_ADD,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
        header = "add a watcher",
        description = "Add a watcher.")
@Deprecated
public class NacosWatchInstanceAdd implements Runnable {

  @CommandLine.Parameters(paramLabel = "<service name>", description = "Instance service name")
  String serviceName;

  @CommandLine.Parameters(paramLabel = "<group name>", description = "Instance group name")
  String groupName;

  @CommandLine.Option(
          names = {"-c", "--cluster"},
          paramLabel = "<clusterName>",
          description = "Cluster name.")
  List<String> clusters;

  @Override
  public void run() {

      System.out.println(LogicHandler.watchService(serviceName,groupName,clusters));

  }
}
