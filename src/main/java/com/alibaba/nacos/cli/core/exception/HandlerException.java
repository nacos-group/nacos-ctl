package com.alibaba.nacos.cli.core.exception;

/**
 * @author lehr
 */
public class HandlerException extends Exception {
    
    public HandlerException(String msg, Exception t) {
        super(msg, t);
    }
    
    public HandlerException(String msg) {
        super(msg);
    }
    
    
}
