package com.alibaba.nacos.ctl.bootstrap;

import com.alibaba.nacos.ctl.command.NacosCtl;
import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import com.alibaba.nacos.ctl.intraction.input.InputGetter;
import com.alibaba.nacos.ctl.bootstrap.utils.StringUtils;
import picocli.CommandLine;

/**
 * 黑窗命令行客户端
 *
 * @author lehr
 */
public class ClientMain {
    
    public static void main(String[] args) throws HandlerException {
        
        System.out.println("NacosCtl Loading...\n");
        InputGetter.init();
        
        System.out.println("Loading Nacos client sdk...\n");
        LogicHandler.init();
        
        new CommandLine(new NacosCtl()).execute(args);
        
        loopExecute(InputGetter.getInstance());
    }
    
    private static void loopExecute(InputGetter in) {
        
        String[] args;
        
        // 循环执行命令
        while (true) {
            
            String line = in.nextLine();
            args = StringUtils.parseInput(line);
            // 忽略无效输入
            if (args.length < 1 || args[0].length() < 1) {
                continue;
            }
            // 给Picocli执行命令
            int ret = new CommandLine(new NacosCtl()).execute(args);
            // 特殊的流程控制，通过返回值来判断,-1是退出，-2是清屏
            if (ret == -1) {
                break;
            }
            if (ret == -2) {
                in.clear();
            }
        }
    }
}
