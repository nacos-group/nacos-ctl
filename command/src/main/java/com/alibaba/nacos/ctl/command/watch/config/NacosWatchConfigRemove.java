package com.alibaba.nacos.ctl.command.watch.config;

import com.alibaba.nacos.ctl.core.LogicHandler;
import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * remove a watcher
 *
 * @author lehr
 */
@CommandLine.Command(name = "remove", sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "remove watcher", description = "Remove a config watcher.")
@Deprecated
public class NacosWatchConfigRemove implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<id>", description = "Listener Id. (Press `Tab` to list Ids)")
    String id;
    
    @Override
    public void run() {
        
        System.out.println(LogicHandler.unwatchConfig(id));
    }
}
