package org.framework.hsven.cxfws.server.ws;

import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Service
@WebService(targetNamespace = Constants.EOMS_MANAGER_TARGET_NAMESPACE, endpointInterface = "org.framework.hsven.cxfws.server.ws.IWebServiceEomsManager")
public class WebServiceEomsManagerImpl implements IWebServiceEomsManager {
    @WebMethod
    @Override
    public String newEvent(@WebParam(name = "serSupplier") String serSupplier,
                           @WebParam(name = "serCaller") String serCaller,
                           @WebParam(name = "callerPwd") String callerPwd,
                           @WebParam(name = "callTime") String callTime,
                           @WebParam(name = "eventDetail") String eventDetail) {
        return EomsResult.of("newEventId", "").toString();
    }

    @WebMethod
    @Override
    public String syncEvent(@WebParam(name = "serSupplier") String serSupplier,
                            @WebParam(name = "serCaller") String serCaller,
                            @WebParam(name = "callerPwd") String callerPwd,
                            @WebParam(name = "callTime") String callTime,
                            @WebParam(name = "eventDetail") String eventDetail) {
        return EomsResult.of("syncEventId", "").toString();
    }
}
