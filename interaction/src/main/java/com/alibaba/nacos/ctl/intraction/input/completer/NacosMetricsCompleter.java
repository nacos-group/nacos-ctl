package com.alibaba.nacos.ctl.intraction.input.completer;

import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;

/**
 * Nacos metrics completer.
 *
 * @author xiweng.yy
 */
public class NacosMetricsCompleter implements NacosCompleterBuilder {
    
    @Override
    public String getCompleterName() {
        return "metrics";
    }
    
    @Override
    public Completer build() {
        Completer metrics = new StringsCompleter("metrics");
        Completer metricsList = new StringsCompleter("jvm", "nacos", "client", "exception");
        return new ArgumentCompleter(metrics, metricsList, NullCompleter.INSTANCE);
    }
}
