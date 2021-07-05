package com.alibaba.nacos.cli.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * clear the terminal, it only works with jline input
 *
 * @author lehr
 */
@CommandLine.Command(
    name = NAME_CLEAR,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = USAGE_CLEAR,
    description = DESCRIPTION_CLEAR)
public class NacosClear implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {
    return -2;
  }
}
