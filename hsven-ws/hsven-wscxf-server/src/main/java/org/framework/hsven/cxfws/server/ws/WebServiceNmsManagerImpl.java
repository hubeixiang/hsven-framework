package org.framework.hsven.cxfws.server.ws;

import org.framework.hsven.cxfws.server.ws.complex.ComplexRequest;
import org.framework.hsven.cxfws.server.ws.complex.ComplexResponse;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Service
@WebService(targetNamespace = Constants.NMS_MANAGER_TARGET_NAMESPACE, endpointInterface = "org.framework.hsven.cxfws.server.ws.IWebServiceNmsManager")
public class WebServiceNmsManagerImpl implements IWebServiceNmsManager {
    @WebMethod
    @Override
    public String syncSheetState(@WebParam(name = "serSupplier") String serSupplier,
                                 @WebParam(name = "serCaller") String serCaller,
                                 @WebParam(name = "callerPwd") String callerPwd,
                                 @WebParam(name = "callTime") String callTime,
                                 @WebParam(name = "eventDetail") String eventDetail) {
        return NmsResult.of("syncSheetStateId", serSupplier).toString();
    }

    @Override
    @WebResult
    public EntityXmlResult syncXmlResult(@WebParam(name = "serSupplier") String serSupplier) {
        return EntityXmlResult.of("a", serSupplier);
    }

    @Override
    @WebResult(header = true)
    public ComplexResponse syncComplex(@WebParam(name = "xyzRequest") ComplexRequest complexRequest) {
        System.out.println(complexRequest);
        return ComplexResponse.rand();
    }
}
