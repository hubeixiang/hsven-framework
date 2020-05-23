package org.framework.hsven.ws.client.service.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * 第一个ws客户端访问处理
 */
public class WsClientBussinessOne extends WebServiceGatewaySupport {

    public String getUrl() {
        return getWebServiceTemplate().getDefaultUri();
    }
}
