package com.alibaba.nacos.cli.config;

import com.alibaba.nacos.cli.config.ConfigLoader.FromPropertie;

import java.util.Properties;

/**
 * Global Config with default value. If conf.properties exists, load it.
 *
 * @author lehr*/
public class GlobalConfig {

  private static GlobalConfig instance = new GlobalConfig();

  public static GlobalConfig getInstance(){
    return instance;
  }

  private String namespaceName = "public";

  private String namespaceId = "";


  @FromPropertie
  private boolean confirmEnabled = true;

  @FromPropertie
  private String host = "localhost";
  @FromPropertie
  private Integer port = 8848;

  @FromPropertie
  private String username = "nacos";
  @FromPropertie
  private String password = "nacos";

  @FromPropertie
  private String accessKey = "accessKey";
  @FromPropertie
  private String secretKey = "secretKey";


  public static Properties getAsProperties(){
    GlobalConfig config = instance;
    Properties properties = new Properties();
    properties.setProperty("serverAddr", config.getHostAddress());
    properties.setProperty("namespace", config.getNamespaceId());
    properties.setProperty("username", config.getUsername());
    properties.setProperty("password", config.getPassword());
    properties.setProperty("accessKey", config.getAccessKey());
    properties.setProperty("secretKey", config.getSecretKey());
    return properties;
  }

  private GlobalConfig(){
    boolean fillAll = ConfigLoader.fill(this);
    if(fillAll){
      System.out.println("Successfully load all configuration from file.\n");
    }
    System.out.println(this);
  }

  @Override
  public String toString() {
    return
            " * namespaceName=" + namespaceName +"\n"+
            " * namespaceId=" + namespaceId +"\n"+
            " * confirmEnabled=" + confirmEnabled +"\n"+
            " * host=" + host +"\n"+
            " * port=" + port +"\n"+
            " * username=" + username +"\n"+
            " * password=" + password +"\n"+
            " * accessKey=" + accessKey +"\n"+
            " * secretKey=" + secretKey+"\n";

  }

  public String getAccessKey() {
    return accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setGlobalNamespace(String name, String id) {
    namespaceName = name;
    namespaceId = id;
  }

  public String getHostAddress() {
    return host + ":" + port;
  }

  public String getHost() {
    return host;
  }

  public Integer getPort() {
    return port;
  }

  public String getNamespaceId() {
    return namespaceId;
  }

  public String getNamespaceName() {
    return namespaceName;
  }

  public boolean isConfirmEnabled() {
    return confirmEnabled;
  }
}
