package com.alibaba.nacos.ctl.core.bean;

/**
 * @author lehr
 */
public class NamespaceVO {
    
    private String name;
    
    private String id;
    
    @Override
    public String toString() {
        return name + "(" + id + ")";
        
    }
    
    public NamespaceVO(String name, String id) {
        this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
