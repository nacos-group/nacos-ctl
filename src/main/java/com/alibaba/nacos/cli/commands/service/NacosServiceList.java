package com.alibaba.nacos.cli.commands.service;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.bean.ServiceVO;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import de.vandermeer.asciitable.AsciiTable;
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
 * list some services intro
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_LIST, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "list service intro", description = "Get a quick look of current services on the nacos-server. If you want to get detail about certain service, please use 'service get' command.")
public class NacosServiceList implements Runnable {
    
    @CommandLine.Option(names = {"-s", "--serviceName"}, paramLabel = "<serviceName>", description = "Service name.")
    String serviceName;
    
    @CommandLine.Option(names = {"-g", "--groupName"}, paramLabel = "<groupName>", description = "Group name.")
    String groupName;
    
    @CommandLine.Option(names = {"-n", "--pageNo"}, paramLabel = "<pageNo>", description = "Page number.")
    Integer pageNo = 1;
    
    @CommandLine.Option(names = {"-i", "--pageSize"}, paramLabel = "<pageSize>", description = "Page size.")
    Integer pageSize = 20;
    
    @Override
    public void run() {
        
        try {
            List<ServiceVO> list = LogicHandler.listServices(serviceName, groupName, pageNo, pageSize);
            int counter = 1;
            AsciiTable at = new AsciiTable();
            at.getContext().setWidth(100);
            at.addRule();
            at.addRow("<Service>", "Service Name", "Group Name", "Healthy Instance Count");
            at.addRule();
            for (ServiceVO bean : list) {
                at.addRow(counter++, bean.getName(), bean.getGroup(), bean.getHealthCount());
            }
            at.addRule();
            System.out.println(at.render());
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
