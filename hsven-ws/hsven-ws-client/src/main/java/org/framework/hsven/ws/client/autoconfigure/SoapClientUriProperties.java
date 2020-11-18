package org.framework.hsven.ws.client.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 要访问的外部的ws服务的地址,有可能有多个
 */
@Component
@ConfigurationProperties("hsven.client.soap-uri")
public class SoapClientUriProperties {

    //要访问的ws地址1
    private String wsUri1;

    //要访问的ws地址2
    private String wsUri2;

    //要访问的ws-eoms的地址
    private String wsEoms;

    public String getWsUri1() {
        return wsUri1;
    }

    public void setWsUri1(String wsUri1) {
        this.wsUri1 = wsUri1;
    }

    public String getWsUri2() {
        return wsUri2;
    }

    public void setWsUri2(String wsUri2) {
        this.wsUri2 = wsUri2;
    }

    public String getWsEoms() {
        return wsEoms;
    }

    public void setWsEoms(String wsEoms) {
        this.wsEoms = wsEoms;
    }
}
