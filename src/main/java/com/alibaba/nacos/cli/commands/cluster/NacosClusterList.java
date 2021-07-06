package com.alibaba.nacos.cli.commands.cluster;

import com.alibaba.nacos.cli.core.LogicHandler;
import de.vandermeer.asciitable.AsciiTable;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_LIST;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * cluster list command handler.
 * @author zzq
 */
@CommandLine.Command(name = NAME_LIST, sortOptions = SORT_OPTIONS,
        headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING,
        descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING,
        optionListHeading = OPTION_LIST_HEADING, header = "list node",
        description = "List node from cluster.")
public class NacosClusterList implements Runnable {
    
    @CommandLine.Option(names = {"-i", "--ip"}, paramLabel = "<ip>", description = "Cluster node ip.")
    String ip = "";
    
    @CommandLine.Option(names = {"-p", "--port"}, paramLabel = "<port>", description = "Cluster node port.")
    String port = "";
    
    @CommandLine.Option(names = {"-n", "--pageNo"}, paramLabel = "<pageNo>", description = "Page number.")
    Integer pageNo = 1;
    
    @CommandLine.Option(names = {"-s", "--pageSize"}, paramLabel = "<pageSize>", description = "Page size.")
    Integer pageSize = 20;
    
    @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
    @Override
    public void run() {
        String keyword = ip;
        if (!(ip.isEmpty() && port.isEmpty())) {
            keyword += ":";
        }
        keyword += port;
        List<Map<String, Object>> list = LogicHandler.listCluster(keyword, pageNo, pageSize);
        AsciiTable at = new AsciiTable();
        at.getContext().setWidth(60);
        at.addRule();
        at.addRow("ip", "port", "state");
        at.addRule();
        for (Map<String, Object> row : list) {
            at.addRow(row.get("ip"), ((Double) row.get("port")).intValue(), row.get("state"));
        }
        at.addRule();
        System.out.println(at.render());
    }
}
