package com.alibaba.nacos.cli;

import com.alibaba.nacos.cli.commands.NacosCtl;
import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import com.alibaba.nacos.cli.input.InputGetter;
import com.alibaba.nacos.cli.utils.StringUtils;
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
