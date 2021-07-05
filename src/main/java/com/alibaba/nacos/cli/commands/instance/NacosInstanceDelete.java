package com.alibaba.nacos.cli.commands.instance;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.input.InputGetter;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * delete an instance
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_DELETE,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = "deregister instance",
    description = "Delete instance from service.")
public class NacosInstanceDelete implements Runnable {

  @CommandLine.Parameters(paramLabel = "<ip>", description = "IP of instance.")
  String ip;

  @CommandLine.Parameters(paramLabel = "<port>", description = "Port of instance.")
  Integer port;

  @CommandLine.Parameters(paramLabel = "<service name>", description = "Instance service name")
  String serviceName;

  @CommandLine.Option(
      names = {"-c", "--cluster"},
      paramLabel = "<clusterName>",
      description = "Cluster name.")
  String clusterName;

  @CommandLine.Option(
      names = {"-g", "--group"},
      paramLabel = "<groupName>",
      description = "Group name.")
  String groupName;

  @CommandLine.Option(
      names = {"-p", "--ephemeral"},
      description = "If instance is ephemeral.")
  boolean ephemeral = true;

  @Override
  public void run() {

    if(InputGetter.cancelConfirm()){
      return ;
    }

    try {
      LogicHandler.deleteInstance(ip, port, clusterName, serviceName, groupName, ephemeral);
      System.out.println("done");
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }
  }
}
