package com.alibaba.nacos.cli.commands;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * switch to different namespace
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_USE,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = USAGE_USE,
    description = DESCRIPTION_USE)
public class NacosUse implements Runnable {

  @CommandLine.Parameters(paramLabel = "<namespace>", description = "Specify a namespace you want to use.")
  String namespace;


  @Override
  public void run() {
    try {
      LogicHandler.useNamespace(namespace);
      System.out.println("done");
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }
  }
}
