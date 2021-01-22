package org.framework.hsven.cxfws.server.ws;

import org.framework.hsven.cxfws.server.ws.complex.ComplexRequest;
import org.framework.hsven.cxfws.server.ws.complex.ComplexResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = Constants.NMS_MANAGER_PORT_TYPE_NAME, targetNamespace = Constants.NMS_MANAGER_TARGET_NAMESPACE)
public interface IWebServiceNmsManager {
    @WebMethod
    public String syncSheetState(@WebParam(name = "serSupplier") String serSupplier,
                                 @WebParam(name = "serCaller") String serCaller,
                                 @WebParam(name = "callerPwd") String callerPwd,
                                 @WebParam(name = "callTime") String callTime,
                                 @WebParam(name = "eventDetail") String eventDetail);

    @WebMethod
    @WebResult
    public EntityXmlResult syncXmlResult(@WebParam(name = "serSupplier") String serSupplier);

    @WebMethod
    @WebResult(header = true)
    public ComplexResponse syncComplex(@WebParam(name = "request") ComplexRequest complexRequest);
}
