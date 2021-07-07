package com.alibaba.nacos.cli.core.bean;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * cluster view object copy nacos com.alibaba.nacos.core.cluster.Member
 * @author zzq
 */
public class ClusterVo {
    
    private String ip;
    
    private int port = -1;
    
    private volatile String state = "UP";
    
    private Map<String, Object> extendInfo = Collections.synchronizedMap(new TreeMap<>());
    
    private String address = "";
    
    private transient int failAccessCnt = 0;
    
    @Override
    public String toString() {
        return "ClusterVo{" + "ip='" + ip + '\'' + ", port=" + port + ", state='" + state + '\'' + ", extendInfo="
                + extendInfo + ", address='" + address + '\'' + ", failAccessCnt=" + failAccessCnt + ", abilities="
                + abilities + '}';
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setFailAccessCnt(int failAccessCnt) {
        this.failAccessCnt = failAccessCnt;
    }
    
    public void setAbilities(Map<String, Object> abilities) {
        this.abilities = abilities;
    }
    
    private Map<String, Object> abilities = new TreeMap<>();
    
    public String getIp() {
        return ip;
    }
    
    public int getPort() {
        return port;
    }
    
    public String getState() {
        return state;
    }
    
    public Map<String, Object> getExtendInfo() {
        return extendInfo;
    }
    
    public String getAddress() {
        return address;
    }
    
    public int getFailAccessCnt() {
        return failAccessCnt;
    }
    
    public Map<String, Object> getAbilities() {
        return abilities;
    }
}
