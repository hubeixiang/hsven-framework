package org.framework.hsven.cxfws.server.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = Constants.EOMS_MANAGER_PORT_TYPE_NAME, targetNamespace = Constants.EOMS_MANAGER_TARGET_NAMESPACE)
public interface IWebServiceEomsManager {
    @WebMethod
    public String newEvent(@WebParam(name = "serSupplier") String serSupplier,
                           @WebParam(name = "serCaller") String serCaller,
                           @WebParam(name = "callerPwd") String callerPwd,
                           @WebParam(name = "callTime") String callTime,
                           @WebParam(name = "eventDetail") String eventDetail);

    @WebMethod
    public String syncEvent(@WebParam(name = "serSupplier") String serSupplier,
                            @WebParam(name = "serCaller") String serCaller,
                            @WebParam(name = "callerPwd") String callerPwd,
                            @WebParam(name = "callTime") String callTime,
                            @WebParam(name = "eventDetail") String eventDetail);
}
