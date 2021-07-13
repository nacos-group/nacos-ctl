package com.alibaba.nacos.cli.commands.cluster;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.bean.ClusterVo;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_GET;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * cluster get command handler.
 * @author zzq
 */
@CommandLine.Command(
        name = NAME_GET,
        sortOptions = SORT_OPTIONS,
        headerHeading = HEADER_HEADING,
        synopsisHeading = SYNOPSIS_HEADING,
        descriptionHeading = DESCRIPTION_HEADING,
        parameterListHeading = PARAMETER_LIST_HEADING,
        optionListHeading = OPTION_LIST_HEADING,
        header = "get metadata",
        description = "get cluster node metadata.")
public class NaocsClusterGet implements Runnable{
    
    @CommandLine.Parameters(paramLabel = "<ip:port>", description = "cluster node metadata.")
    String keyword;
    
    @Override
    public void run() {
        try {
            List<ClusterVo> clusterVos = LogicHandler.listCluster(keyword, 1, 1);
            if (clusterVos.size() > 1) {
                throw new HandlerException("Multiple data found, Please enter the complete ip:port");
            }
            System.out.println(clusterVos.isEmpty() ? "" : clusterVos.get(0).getExtendInfo());
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
