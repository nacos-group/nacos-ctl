package com.alibaba.nacos.cli.commands.cluster;

import com.alibaba.nacos.cli.commands.config.NacosConfigGet;
import picocli.CommandLine;

import static com.alibaba.nacos.cli.utils.HintUtils.CLUSTER;
import static com.alibaba.nacos.cli.utils.HintUtils.CLUSTER_CONFIG;
import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_CLUSTER;
import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * cluster subcommand.
 *
 * @author zzq
 */
@CommandLine.Command(name = CLUSTER, sortOptions = SORT_OPTIONS,
        headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING,
        descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING,
        optionListHeading = OPTION_LIST_HEADING, header = CLUSTER_CONFIG, description = DESCRIPTION_CLUSTER,
        subcommands = {CommandLine.HelpCommand.class, NacosClusterList.class, NaocsClusterGet.class, NaocsClusterDelete.class})
public class NacosCluster implements Runnable {
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
