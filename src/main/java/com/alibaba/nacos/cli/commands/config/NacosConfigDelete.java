package com.alibaba.nacos.cli.commands.config;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.input.InputGetter;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * delete a config
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
    header = "delete configurations",
    description = "It deletes configurations in Nacos.")
public class NacosConfigDelete implements Runnable {

  @CommandLine.Parameters(paramLabel = "<group>", description = "Configuration group.")
  String group;

  @CommandLine.Parameters(paramLabel = "<dataId>", description = "Configuration ID.")
  String dataId;


  @Override
  public void run() {

    if(InputGetter.cancelConfirm()){
      return ;
    }

    try {
      LogicHandler.deleteConfig(group, dataId);
      System.out.println("done");
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }
  }
}
