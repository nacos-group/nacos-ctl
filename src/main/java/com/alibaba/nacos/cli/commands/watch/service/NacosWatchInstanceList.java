package com.alibaba.nacos.cli.commands.watch.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_LIST;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * get the watcher list
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_LIST, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "list all watchers", description = "List all watchers")
@Deprecated
public class NacosWatchInstanceList implements Runnable {
    
    @Override
    public void run() {
        
        List<String> list = LogicHandler.listServiceWatches();
        list.forEach(System.out::println);
    }
}
