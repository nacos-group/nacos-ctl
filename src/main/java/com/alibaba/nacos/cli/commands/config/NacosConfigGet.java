package com.alibaba.nacos.cli.commands.config;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.google.common.io.Files;
import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * get a config
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
    header = "get configurations",
    description = "This API is used to get configurations in Nacos.")
public class NacosConfigGet implements Runnable {

  @CommandLine.Parameters(paramLabel = "<group>", description = "Configuration group.")
  String group;

  @CommandLine.Parameters(paramLabel = "<dataId>", description = "Configuration ID.")
  String dataId;

  @CommandLine.Option(
      names = {"-f", "--file"},
      paramLabel = "<file>",
      description = "The config file path you wanna export.")
  File file;


  @Override
  public void run() {
    try {
      String content = LogicHandler.getConfig(group, dataId);
      if (file == null) {
        System.out.println(content);
      } else {

        try {
          Files.touch(file);
        } catch (IOException e) {
          System.out.println("directory not exist!");
          return;
        }

        try {
          Files.write(content.getBytes(), file);
        } catch (FileNotFoundException e) {
          System.out.println("file not exists!");
        } catch (IOException e) {
          System.out.println("failed to write file");
        }
      }
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }

  }
}
