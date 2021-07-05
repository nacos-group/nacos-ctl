package com.alibaba.nacos.cli.core.service.openapi.network.bean;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * 由于Apache HttpClient的原版Delete不支持传参，所以需要自己继承实现
 *
 * @author lehr
 */
public class HttpDelete extends HttpEntityEnclosingRequestBase {
  public static final String METHOD_NAME = "DELETE";

  @Override
  public String getMethod() {
    return METHOD_NAME;
  }

  public HttpDelete(final String uri) {
    super();
    setURI(URI.create(uri));
  }

  public HttpDelete(final URI uri) {
    super();
    setURI(uri);
  }

  public HttpDelete() {
    super();
  }
}
