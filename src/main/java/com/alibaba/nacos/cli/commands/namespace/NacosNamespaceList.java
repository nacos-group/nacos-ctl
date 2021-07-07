package com.alibaba.nacos.cli.commands.namespace;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.bean.NamespaceVO;
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
 * get the list of all the namespaces from the server.
 *
 * @author lehr
 */
@CommandLine.Command(name = NAME_LIST, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "list all namespaces", description = "This API is used to get all namespaces.")
public class NacosNamespaceList implements Runnable {
    
    @Override
    public void run() {
        try {
            List<NamespaceVO> list = LogicHandler.listNamespaces();
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Namespace Name", "Namespace Id");
            at.addRule();
            for (NamespaceVO bean : list) {
                at.addRow(bean.getName(), bean.getId());
            }
            at.addRule();
            System.out.println(at.render(80));
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
