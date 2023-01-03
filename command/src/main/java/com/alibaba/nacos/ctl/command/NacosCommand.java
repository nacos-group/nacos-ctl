package com.alibaba.nacos.ctl.command;

import picocli.CommandLine;

/**
 * Nacos command.
 *
 * @author xiweng.yy
 */
public abstract class NacosCommand implements Runnable {
    
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    
    /**
     * Get command name.
     *
     * @return command name
     */
    public abstract String getCommandName();
    
    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }
}
