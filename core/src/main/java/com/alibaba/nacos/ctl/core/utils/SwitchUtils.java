package com.alibaba.nacos.ctl.core.utils;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lehr
 */
public class SwitchUtils {
    
    
    public static String[] translateMap(String entry, String key, String value, String mode) throws HandlerException {
        
        //首先需要补全内容，也就是再拉一次数据，然后再修改参数，再发送
        
        //check name
        String oldValue = LogicHandler.getSwitchValueByName(entry);
        //去掉双括号
        String results = oldValue.substring(1, oldValue.length() - 1);
        //拆分获取参数转成map
        String[] params = results.split(",");
        Map<String, String> map = new HashMap<>();
        for (String p : params) {
            if (p.contains(":")) {
                String[] kv = p.split(":");
                map.put(kv[0].replace("\"", ""), kv[1]);
            }
        }
        
        if ("add".equals(mode)) {
            map.put(key, value);
        }
        
        if ("remove".equals(mode)) {
            map.remove(key);
        }
        StringBuffer sb = new StringBuffer();
        map.forEach((k, v) -> {
            sb.append(k).append(":").append(v).append(",");
        });
        
        String ret = sb.toString();
        if (ret.trim().length() < 1) {
            ret = "  ";
        }
        
        return new String[] {entry, ret};
    }
    
    /**
     * Switch的展示名称和实际控制的开关名称不统一，这里做一个输入转换
     *
     * <p>目前参考2.0.1的SwitchManager实现
     *
     * <p>并不是一个很好的设计
     *
     * @param entry
     * @param value
     * @return
     */
    public static String[] translate(String entry, String value) {
        
        //check name
        
        //push xx version系列有格式要求，value是 xx:x.x.x
        if ("pushJavaVersion".equals(entry)) {
            return new String[] {"pushVersion", "java:" + value};
        }
        if ("pushGoVersion".equals(entry)) {
            return new String[] {"pushVersion", "go:" + value};
        }
        if ("pushPythonVersion".equals(entry)) {
            return new String[] {"pushVersion", "python:" + value};
        }
        if ("pushCVersion".equals(entry)) {
            return new String[] {"pushVersion", "c:" + value};
        }
        if ("pushCSharpVersion".equals(entry)) {
            //实际上server不支持这个
            return new String[] {"pushVersion", "csharp:" + value};
        }
        
        if ("healthCheckEnabled".equals(entry)) {
            return new String[] {"check", value};
        }
        if ("checkTimes".equals(entry)) {
            return new String[] {"healthCheckTimes", value};
        }
        if ("distroEnabled".equals(entry)) {
            return new String[] {"distro", value};
        }
        if ("defaultPushCacheMillis".equals(entry)) {
            return new String[] {"pushCacheMillis", value};
        }
        
        return new String[] {entry, value};
    }
    
}
