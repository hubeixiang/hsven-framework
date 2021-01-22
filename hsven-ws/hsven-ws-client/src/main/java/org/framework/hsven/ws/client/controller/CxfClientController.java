package org.framework.hsven.ws.client.controller;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CxfClientController {

    private Client cxfClient;

    public Client getClient() {
        if (cxfClient == null) {
            connectCxfClient();
        }
        return cxfClient;
    }

    private synchronized void connectCxfClient() {
        if (cxfClient == null) {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            cxfClient = dcf.createClient("http://127.0.0.1:38622/proxy-cxfws-server/ws/SheetStateSync?wsdl");
        }
    }

    @RequestMapping("syncSheetState")
    @ResponseBody
    public Object syncSheetState() throws Exception {
        return getClient().invoke("syncSheetState", "syncSheetState_p", "1", "2", "3", "4");
    }

    @RequestMapping("syncXmlResult")
    @ResponseBody
    public Object syncXmlResult() throws Exception {
        return getClient().invoke("syncXmlResult", "syncXmlResult_p", "1", "2", "3", "4");
    }

    @RequestMapping("syncComplex")
    @ResponseBody
    public Object syncComplex() throws Exception {
        Object request = new Object();
//        ComplexRequest request = new ComplexRequest();
//        request.setId("ws-client");
//        XMLGregorianCalendarImpl time = new XMLGregorianCalendarImpl();
//        request.setTime(time);
        return getClient().invoke("syncComplex", request);
    }

    @RequestMapping("syncComplexJson")
    @ResponseBody
    public Object syncComplexJson() throws Exception {
        String json = "{\"id\":\"req_id\",\"time\":1611145225405,\"interEntity\":{\"str\":\"req_str\",\"number\":2,\"time\":1611145225404},\"request\":true}";
        return cxfClient.invoke("syncComplex", json);
    }

    @RequestMapping("syncComplexJsonArray")
    @ResponseBody
    public Object syncComplexJsonArray() throws Exception {
        String json = "[{\"id\":\"req_id\",\"time\":1611145225405,\"interEntity\":{\"str\":\"req_str\",\"number\":2,\"time\":1611145225404},\"request\":true}]";
        return cxfClient.invoke("syncComplex", json);
    }
}
