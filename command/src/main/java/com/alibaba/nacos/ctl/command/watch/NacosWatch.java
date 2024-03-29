package com.alibaba.nacos.ctl.command.watch;

import com.alibaba.nacos.ctl.command.watch.config.NacosWatchConfig;
import com.alibaba.nacos.ctl.command.watch.service.NacosWatchInstance;
import picocli.CommandLine;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * watch subcommand
 *
 * @author lehr
 */
@CommandLine.Command(name = "watch", sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "watch service/config.", description = "Set a listener to watch service/config changes.", subcommands = {
        CommandLine.HelpCommand.class, NacosWatchConfig.class, NacosWatchInstance.class})
public class NacosWatch implements Runnable {
    
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
