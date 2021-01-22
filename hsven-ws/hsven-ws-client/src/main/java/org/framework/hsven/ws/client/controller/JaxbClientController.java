package org.framework.hsven.ws.client.controller;

import com.hios.wservice.proxy.eoms.service.SheetVerifyRequest;
import com.hios.wservice.proxy.eoms.service.SheetVerifyScheduleRequest;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JaxbClientController {

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
            cxfClient = dcf.createClient("http://127.0.0.1:38620/proxy-ws-server/ws/eoms.wsdl");
        }
    }

    @RequestMapping("sheetVerify/eoms")
    @ResponseBody
    public Object sheetVerify() throws Exception {
        SheetVerifyRequest sheetVerifyRequest = new SheetVerifyRequest();
        return getClient().invoke("sheetVerify", sheetVerifyRequest);
    }
}
