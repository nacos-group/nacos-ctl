package com.alibaba.nacos.cli.commands.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * update a service
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_UPDATE,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = "update a service",
    description = "This API is used to modify a service info.")
public class NacosServiceUpdate implements Runnable {

  @CommandLine.Parameters(paramLabel = "<serviceName>", description = "Service name.")
  String serviceName;

  @CommandLine.Option(
      names = {"-s", "--selector"},
      paramLabel = "<selector>",
      description = "Visit strategy, please input JSON string.")
  String selector;

  @CommandLine.Option(
      names = {"-g", "--groupName"},
      paramLabel = "<groupName>",
      description = "Group name.")
  String groupName;

  @CommandLine.Option(
      names = {"-t", "--threshold"},
      paramLabel = "<threshold>",
      description = "Set value from 0 to 1, default 0.")
  float threshold;

  @CommandLine.Option(
      names = {"-m", "--metadata"},
      paramLabel = "<metadata>",
      description = "Metadata of service.")
  String metadata;

  @Override
  public void run() {
    try {
      LogicHandler.updateService(groupName, serviceName, threshold, metadata, selector);
      System.out.println("done");
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }
  }
}
