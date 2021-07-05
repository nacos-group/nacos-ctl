package com.alibaba.nacos.cli.core.bean;

/**
 * @author lehr
 */
public class ConfigVO {

    private String dataId;
    private String groupName;

    public ConfigVO(String dataId,String groupName){
        this.dataId = dataId;
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "ConfigVO{" +
                "dataId='" + dataId + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
