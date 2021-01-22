package org.framework.hsven.jaxb.server.ws;


import com.hios.nms.jaxbwsservice.SheetVerifyRequest;
import com.hios.nms.jaxbwsservice.SheetVerifyResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NmsEndpoint {

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "syncSheetState")
    @ResponsePayload
    public String syncSheetStateMethod(String serSupplier,
                                       String serCaller,
                                       String callerPwd,
                                       String callTime,
                                       String eventDetail) {
        return String.format("%s-%s-%s-%s-%s", serSupplier, serCaller, callerPwd, callTime, eventDetail);
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "sheetVerifyRequest")
    @ResponsePayload
    public SheetVerifyResponse sheetVerify(@RequestPayload SheetVerifyRequest request) {
        SheetVerifyResponse response = new SheetVerifyResponse();
        response.setResult(String.format("code=%s;data=%s,request=%s", 200, "nmsSheetVerify", request.getFileUrl()));
        return response;
    }
}
