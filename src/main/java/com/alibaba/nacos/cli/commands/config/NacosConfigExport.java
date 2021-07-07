package com.alibaba.nacos.cli.commands.config;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.google.common.io.Files;
import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.alibaba.nacos.cli.utils.HintUtils.DESCRIPTION_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.HEADER_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.NAME_EXPORT;
import static com.alibaba.nacos.cli.utils.HintUtils.OPTION_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.PARAMETER_LIST_HEADING;
import static com.alibaba.nacos.cli.utils.HintUtils.SORT_OPTIONS;
import static com.alibaba.nacos.cli.utils.HintUtils.SYNOPSIS_HEADING;

/**
 * get a config from nacos server and export it as a file
 *
 * @author lehr
 */
@Deprecated
@CommandLine.Command(name = NAME_EXPORT, sortOptions = SORT_OPTIONS, headerHeading = HEADER_HEADING, synopsisHeading = SYNOPSIS_HEADING, descriptionHeading = DESCRIPTION_HEADING, parameterListHeading = PARAMETER_LIST_HEADING, optionListHeading = OPTION_LIST_HEADING, header = "export configurations", description = "Get a config from nacos server and export it as a file.")
public class NacosConfigExport implements Runnable {
    
    @CommandLine.Parameters(paramLabel = "<group>", description = "Configuration group.")
    String group;
    
    @CommandLine.Parameters(paramLabel = "<dataId>", description = "Configuration ID.")
    String dataId;
    
    @CommandLine.Parameters(paramLabel = "<file>", description = "The config file path you wanna export.")
    File file;
    
    
    @Override
    public void run() {
        
        try {
            Files.touch(file);
        } catch (IOException e) {
            System.out.println("directory not exist!");
            return;
        }
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] bytes = LogicHandler.getConfig(group, dataId).getBytes();
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            System.out.println("file not exists!");
        } catch (IOException e) {
            System.out.println("failed to write file");
        } catch (HandlerException e) {
            System.out.println(e.getMessage());
        }
    }
}
