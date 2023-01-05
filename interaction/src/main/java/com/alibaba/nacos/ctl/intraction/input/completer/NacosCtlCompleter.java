package com.alibaba.nacos.ctl.intraction.input.completer;

import com.alibaba.nacos.ctl.core.exception.HandlerException;
import jline.console.completer.Completer;
import jline.internal.Preconditions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * NacosCtl全局指令自动补全器
 *
 * <p>整个程序的各种子指令的补全逻辑都在这里拼接
 *
 * @author lehr
 */
public class NacosCtlCompleter implements Completer {
    
    private List<Completer> completers;
    
    public NacosCtlCompleter() throws HandlerException {
        try {
            completers = CompleterFactory.loadAll();
            completers.addAll(CompleterFactory.loadExtensionCompleter());
        } catch (Exception e) {
            throw new HandlerException("failed to load completer groups", e);
        }
    }
    
    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        Preconditions.checkNotNull(candidates);
        List<Completion> completions = new ArrayList(this.completers.size());
        int max = -1;
        Iterator var6 = this.completers.iterator();
        
        while (var6.hasNext()) {
            Completer completer = (Completer) var6.next();
            Completion completion = new Completion(candidates);
            completion.complete(completer, buffer, cursor);
            max = Math.max(max, completion.cursor);
            completions.add(completion);
        }
        
        var6 = completions.iterator();
        
        while (var6.hasNext()) {
            Completion completion = (Completion) var6.next();
            if (completion.cursor == max) {
                candidates.addAll(completion.candidates);
            }
        }
        
        return max;
    }
    
    private class Completion {
        
        public final List<CharSequence> candidates;
        
        public int cursor;
        
        public Completion(List<CharSequence> candidates) {
            Preconditions.checkNotNull(candidates);
            this.candidates = new LinkedList(candidates);
        }
        
        public void complete(Completer completer, String buffer, int cursor) {
            Preconditions.checkNotNull(completer);
            this.cursor = completer.complete(buffer, cursor, this.candidates);
        }
    }
}
