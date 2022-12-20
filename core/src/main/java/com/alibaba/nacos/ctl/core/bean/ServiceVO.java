package com.alibaba.nacos.ctl.core.bean;

/**
 * @author lehr
 */
public class ServiceVO {
    
    private String name;
    
    private String group;
    
    private String healthCount;
    
    public ServiceVO(String name, String group, String healthCount) {
        this.group = group;
        this.name = name;
        this.healthCount = healthCount;
    }
    
    @Override
    public String toString() {
        return "ServiceVO{" + "name='" + name + '\'' + ", group='" + group + '\'' + ", healthCount='" + healthCount
                + '\'' + '}';
    }
    
    public String getName() {
        return name;
    }
    
    public String getGroup() {
        return group;
    }
    
    public String getHealthCount() {
        return healthCount;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
    
    public void setHealthCount(String healthCount) {
        this.healthCount = healthCount;
    }
}
