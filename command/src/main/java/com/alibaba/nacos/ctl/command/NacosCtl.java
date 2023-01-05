package com.alibaba.nacos.ctl.command;

import com.alibaba.nacos.ctl.command.config.NacosConfig;
import com.alibaba.nacos.ctl.command.instance.NacosInstance;
import com.alibaba.nacos.ctl.command.namespace.NacosNamespace;
import com.alibaba.nacos.ctl.command.service.NacosService;
import com.alibaba.nacos.ctl.command.switches.NacosSwitch;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import static com.alibaba.nacos.ctl.command.utils.HintUtils.APP_NAME;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.CLI_DESCRIPTION;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.COMMAND_LIST_HEADING;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.FOOTER;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.MIXIN_STANDARD_HELP_OPTIONS;
import static com.alibaba.nacos.ctl.command.utils.HintUtils.VERSION_NAME;

/**
 * the main command, based on picocli
 *
 * @author lehr
 */
@Command(name = APP_NAME, mixinStandardHelpOptions = MIXIN_STANDARD_HELP_OPTIONS, version = VERSION_NAME, description = CLI_DESCRIPTION, commandListHeading = COMMAND_LIST_HEADING, footer = FOOTER, subcommands = {
        CommandLine.HelpCommand.class, NacosQuit.class, NacosClear.class, NacosConfig.class, NacosNamespace.class,
        NacosInstance.class, NacosSwitch.class, NacosService.class,
        NacosUse.class,
        // NacosWatch.class,
})
public class NacosCtl extends NacosCommand {
    
    @Override
    public String getCommandName() {
        return APP_NAME;
    }
}
