package org.framework.hsven.ws.client.service.client;

import com.hios.wservice.proxy.eoms.service.CheckImportRequest;
import com.hios.wservice.proxy.eoms.service.CheckImportResponse;
import com.hios.wservice.proxy.eoms.service.QuitOrderUpdateRequest;
import com.hios.wservice.proxy.eoms.service.QuitOrderUpdateResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * 第一个ws客户端访问处理
 */
public class WsClientBussinessEoms extends WebServiceGatewaySupport {

    public String getUrl() {
        return getWebServiceTemplate().getDefaultUri();
    }

    /**
     * 两种请求方式
     * 带SoapActionCallback,与不带SoapActionCallback都可以正常访问
     *
     * @return
     */

    public Object checkImport() {
        CheckImportRequest checkImportRequest = new CheckImportRequest();
        checkImportRequest.setOrderId("xyz_abc");
        CheckImportResponse checkImportResponse = (CheckImportResponse) getWebServiceTemplate().marshalSendAndReceive(checkImportRequest, new SoapActionCallback("checkImport"));
        return checkImportResponse.getResult();
    }

    public Object quitOrderUpdate() {
        QuitOrderUpdateRequest quitOrderUpdateRequest = new QuitOrderUpdateRequest();
        quitOrderUpdateRequest.setOrderId("xyz_123");
        QuitOrderUpdateResponse quitOrderUpdateResponse = (QuitOrderUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(quitOrderUpdateRequest);
        return quitOrderUpdateResponse.getResult();
    }
}
