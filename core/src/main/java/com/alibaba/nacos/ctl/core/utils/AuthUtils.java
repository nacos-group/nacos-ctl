package com.alibaba.nacos.ctl.core.utils;

import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.client.config.impl.SpasAdapter;
import com.alibaba.nacos.client.naming.utils.SignUtil;
import com.alibaba.nacos.common.constant.HttpHeaderConsts;
import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;

import java.util.Map;

/**
 * Auth utils
 *
 * @author xiweng.yy
 */
public class AuthUtils {
    
    public static Header buildConfigSpas(Map<String, String> paramValues, String accessKey, String secretKey) {
        Header header = Header.newInstance();
        header.getHeader().remove(HttpHeaderConsts.CONTENT_TYPE);
        if (StringUtils.isNotEmpty(accessKey) && StringUtils.isNotEmpty(secretKey)) {
            header.addParam("Spas-AccessKey", accessKey);
            Map<String, String> signHeaders = SpasAdapter.getSignHeaders(paramValues, secretKey);
            if (signHeaders != null) {
                header.addAll(signHeaders);
            }
        }
        String ts = String.valueOf(System.currentTimeMillis());
        
        header.addParam(Constants.CLIENT_REQUEST_TS_HEADER, ts);
        header.addParam("exConfigInfo", "true");
        header.addParam(HttpHeaderConsts.REQUEST_ID, UuidUtils.generateUuid());
        return header;
    }
    
    public static void injectNamingSpas(Map<String, String> paramValues, String accessKey, String secretKey) {
        if (StringUtils.isNotBlank(accessKey) && StringUtils.isNotBlank(secretKey)) {
            try {
                String signData = getSignData(paramValues.get("serviceName"));
                String signature = SignUtil.sign(signData, secretKey);
                paramValues.put("signature", signature);
                paramValues.put("data", signData);
                paramValues.put("ak", accessKey);
            } catch (Exception e) {
                System.out.println("inject ak/sk failed.");
                e.printStackTrace();
            }
        }
    }
    
    private static String getSignData(String serviceName) {
        return StringUtils.isNotEmpty(serviceName) ? System.currentTimeMillis() + "@@" + serviceName
                : String.valueOf(System.currentTimeMillis());
    }
    
    public static void injectIdentity(Header header, String identityKey, String identityValue) {
        if (StringUtils.isNotBlank(identityKey) && StringUtils.isNotBlank(identityValue)) {
            header.addParam(identityKey, identityValue);
        }
    }
}
