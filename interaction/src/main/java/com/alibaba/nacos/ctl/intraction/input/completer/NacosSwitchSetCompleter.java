package com.alibaba.nacos.ctl.intraction.input.completer;

import com.alibaba.nacos.ctl.core.LogicHandler;
import com.alibaba.nacos.ctl.core.exception.HandlerException;
import jline.console.completer.Completer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Use命令自动补全可用的namespace
 */
class NacosSwitchSetCompleter implements Completer {
    
    private SortedSet<String> strings = new TreeSet<String>();
    
    
    @Override
    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        
        // 找服务器刷新可访问的数据类型
        if (strings.isEmpty()) {
            Map<String, String> switches = null;
            try {
                switches = LogicHandler.getSwitches();
            } catch (HandlerException e) {
                switches = Collections.emptyMap();
            }
            strings.addAll(switches.keySet());
            strings.add("all");
        }
        
        // 如果目前没有输入，则列出所有选项
        if (buffer == null) {
            candidates.addAll(strings);
        } else {
            // 进行前缀匹配，把match的加入进去
            for (String match : strings.tailSet(buffer)) {
                if (!match.startsWith(buffer)) {
                    break;
                }
                candidates.add(match);
            }
        }
        return candidates.isEmpty() ? -1 : 0;
    }
}
