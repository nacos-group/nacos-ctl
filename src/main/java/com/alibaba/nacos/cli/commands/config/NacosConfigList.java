package com.alibaba.nacos.cli.commands.config;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.bean.ConfigVO;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.input.InputGetter;
import de.vandermeer.asciitable.AsciiTable;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * list config
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_LIST,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = "list configurations",
    description = "List configurations from server.")
public class NacosConfigList implements Runnable {

  @CommandLine.Option(
      names = {"-g", "--group"},
      paramLabel = "<group>",
      description = "Configuration group.")
  String group = "";

  @CommandLine.Option(
      names = {"-d", "--dataId"},
      paramLabel = "<dataId>",
      description = "Configuration ID.")
  String dataId = "";

  @CommandLine.Option(
      names = {"-n", "--pageNo"},
      paramLabel = "<pageNo>",
      description = "Page number.")
  Integer pageNo = 1;

  @CommandLine.Option(
      names = {"-i", "--pageSize"},
      paramLabel = "<pageSize>",
      description = "Page size.")
  Integer pageSize = 20;

  @Override
  public void run() {

    try{
      List<ConfigVO> list = LogicHandler.listConfigs(dataId, group, pageNo, pageSize);
      int counter = 1;
      AsciiTable at = new AsciiTable();
      at.getContext().setWidth(60);
      at.addRule();
      at.addRow("<Config>","Data Id", "Group Id");
      at.addRule();
      for (ConfigVO bean : list) {
        at.addRow(counter++,bean.getDataId(),bean.getGroupName());
      }
      at.addRule();
      System.out.println(at.render());
    }catch (HandlerException e){
      System.out.println(e.getMessage());
    }

  }
}
