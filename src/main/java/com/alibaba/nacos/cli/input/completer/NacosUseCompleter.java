package com.alibaba.nacos.cli.input.completer;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.bean.NamespaceVO;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import jline.console.completer.Completer;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Use命令自动补全可用的namespace
 */
class NacosUseCompleter implements Completer {
    
    private SortedSet<String> strings = new TreeSet<String>();
    
    
    @Override
    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        
        // 找服务器刷新可访问的数据类型
        strings.clear();
        List<NamespaceVO> namespaces = null;
        try {
            namespaces = LogicHandler.listNamespaces();
        } catch (HandlerException e) {
            namespaces = Collections.EMPTY_LIST;
        }
        
        strings.addAll(namespaces.stream().map(NamespaceVO::toString).collect(Collectors.toList()));
        
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
