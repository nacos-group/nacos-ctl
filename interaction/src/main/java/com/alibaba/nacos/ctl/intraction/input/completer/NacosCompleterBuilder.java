package com.alibaba.nacos.ctl.intraction.input.completer;

import jline.console.completer.Completer;

/**
 * Nacos completer builder
 *
 * @author xiweng.yy
 */
public interface NacosCompleterBuilder {
    
    /**
     * Get completer name.
     *
     * @return completer name
     */
    String getCompleterName();
    
    /**
     * Build target completer.
     *
     * @return completer
     */
    Completer build();
}
