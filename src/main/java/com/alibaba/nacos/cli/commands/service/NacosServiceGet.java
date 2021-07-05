package com.alibaba.nacos.cli.commands.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * get a service
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_GET,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = "get service",
    description = "This API is used to get a service info.")
public class NacosServiceGet implements Runnable {

  @CommandLine.Parameters(paramLabel = "<serviceName>", description = "Service name.")
  String serviceName;

  @CommandLine.Option(
      names = {"-g", "--groupName"},
      paramLabel = "<groupName>",
      description = "Group name.")
  String groupName;

  @Override
  public void run() {
    try {
      System.out.println(LogicHandler.getService(groupName, serviceName));
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }
  }
}
