package org.framework.hsven.ws.client.service.client;

import com.hios.nms.jaxbwsservice.SheetVerifyRequest;
import com.hios.nms.jaxbwsservice.SheetVerifyResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * 第一个ws客户端访问处理
 */
public class WsClientBussinessJaxb extends WebServiceGatewaySupport {

    public String getUrl() {
        return getWebServiceTemplate().getDefaultUri();
    }

    public Object sheetVerifyAndCallback() {
        SheetVerifyRequest request = new SheetVerifyRequest();
        request.setFileUrl("xfile");
        request.setObjectClass("xobj");
        request.setUserId("xuserId");
        SheetVerifyResponse sheetVerifyResponse = (SheetVerifyResponse) getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback("sheetVerifyRequest"));
        return sheetVerifyResponse.getResult();
    }

    public Object sheetVerify() {
        SheetVerifyRequest request = new SheetVerifyRequest();
        request.setFileUrl("xfile");
        request.setObjectClass("xobj");
        request.setUserId("xuserId");
        SheetVerifyResponse sheetVerifyResponse = (SheetVerifyResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return sheetVerifyResponse.getResult();
    }

    public Object syncSheetStateMethod() {
        String serSupplier = "a";
        String serCaller = "b";
        String callerPwd = "c";
        String callTime = "d";
        String eventDetail = "e";
        String[] args = new String[]{serSupplier, serCaller, callerPwd, callTime, eventDetail};
        String uri = String.format("serSupplier=%s,serCaller=%s,callerPwd=%s,callTime=%s,eventDetail=%s",
                serSupplier, serCaller, callerPwd, callTime, eventDetail);
        String result = (String) getWebServiceTemplate().marshalSendAndReceive(args);
        return result;
    }
}
