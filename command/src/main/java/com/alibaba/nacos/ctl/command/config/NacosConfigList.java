package com.alibaba.nacos.ctl.command.config;

import com.alibaba.nacos.api.utils.StringUtils;
import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.bean.ConfigVO;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import de.vandermeer.asciitable.AsciiTable;
import picocli.CommandLine;

import java.util.List;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.NAME_LIST;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * list config
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_LIST, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "list configurations", description = "List configurations from server.")
public class NacosConfigList implements Runnable {
    
    @CommandLine.Option(names = {"-g", "--group"}, paramLabel = "<group>", description = "Configuration group.")
    String group = "";
    
    @CommandLine.Option(names = {"-d", "--dataId"}, paramLabel = "<dataId>", description = "Configuration ID.")
    String dataId = "";
    
    @CommandLine.Option(names = {"-n", "--pageNo"}, paramLabel = "<pageNo>", description = "Page number.")
    Integer pageNo = 1;
    
    @CommandLine.Option(names = {"-i", "--pageSize"}, paramLabel = "<pageSize>", description = "Page size.")
    Integer pageSize = 20;
    
    @Override
    public void run() {
        String search = "accurate";
        try {
            if (StringUtils.isEmpty(group)) {
                group = "**";
                search = "blur";
            }
            if (StringUtils.isEmpty(dataId)) {
                dataId = "**";
                search = "blur";
            }
            List<ConfigVO> list = LogicHandler.listConfigs(dataId, group, pageNo, pageSize, search);
            int counter = 1;
            AsciiTable at = new AsciiTable();
            at.getContext().setWidth(60);
            at.addRule();
            at.addRow("<Config>", "Data Id", "Group Id");
            at.addRule();
            for (ConfigVO bean : list) {
                at.addRow(counter++, bean.getDataId(), bean.getGroupName());
            }
            at.addRule();
            System.out.println(at.render());
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
        
    }
}
