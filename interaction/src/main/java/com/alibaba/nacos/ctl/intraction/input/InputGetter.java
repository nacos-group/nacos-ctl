package com.alibaba.nacos.ctl.intraction.input;

import com.alibaba.nacos.ctl.core.config.GlobalConfig;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import com.alibaba.nacos.ctl.intraction.input.completer.NacosCtlCompleter;
import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * JLine实现的输入行
 *
 * <p>有可能会出现创建出错的情况，那样的话只能用默认的Scanner来
 *
 * <p>整个的补全逻辑都在completer包下，自己写了一整套补全逻辑
 *
 * @author lehr
 */
public class InputGetter {
    
    private static InputGetter instance = new InputGetter();
    
    public static InputGetter getInstance() {
        return instance;
    }
    
    private ConsoleReader console;
    
    private GlobalConfig config = GlobalConfig.getInstance();
    
    public static void init() throws HandlerException {
        try {
            instance.console = new ConsoleReader();
            instance.console.setHandleUserInterrupt(true);
            instance.console.addCompleter(new NacosCtlCompleter());
        } catch (IOException e) {
            throw new HandlerException("Failed to load JLine", e);
        }
    }
    
    /**
     * 对于删除等请求，需要让用户再确认下
     *
     * @return
     */
    public static boolean cancelConfirm() {
        
        if (!GlobalConfig.getInstance().isConfirmEnabled()) {
            return false;
        }
        
        try {
            String s = instance.console.readLine("confirm operation? (y/n)  ").trim();
            boolean cancel = !("yes".equals(s) || "y".equals(s));
            if (cancel) {
                System.out.println("operation canceled!");
            }
            return cancel;
        } catch (IOException e) {
            return true;
        }
    }
    
    public String nextLine() {
        
        try {
            return console.readLine(getPrompt());
        } catch (IOException e) {
            return "";
        }
    }
    
    
    public void clear() {
        try {
            console.clearScreen();
        } catch (IOException e) {
            System.out.println("fail to clear screen");
        }
    }
    
    /**
     * 给客户端提供prompt文字（每次刷新一次信息）
     *
     * @return
     */
    public String getPrompt() {
        StringBuffer sb = new StringBuffer();
        sb.append("[NacosCtl:").append(config.getHostAddress()).append("(").append(config.getNamespaceName())
                .append(")]>");
        return sb.toString();
    }
}
