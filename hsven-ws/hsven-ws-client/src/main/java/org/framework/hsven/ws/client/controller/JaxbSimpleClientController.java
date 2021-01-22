package org.framework.hsven.ws.client.controller;

import com.hios.nms.jaxbwsservice.SheetVerifyRequest;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.framework.hsven.ws.client.service.client.WsClientBussinessJaxb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JaxbSimpleClientController {

    @Autowired
    private WsClientBussinessJaxb wsClientBussinessJaxb;

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
            cxfClient = dcf.createClient("http://127.0.0.1:38623/proxy-jaxb-server/ws/nms.wsdl");
        }
    }

    @RequestMapping("sheetVerify/nms")
    @ResponseBody
    public Object sheetVerify() throws Exception {
        SheetVerifyRequest sheetVerifyRequest = new SheetVerifyRequest();
        return getClient().invoke("sheetVerify", sheetVerifyRequest);
    }

    @RequestMapping("syncSheetState/nms")
    @ResponseBody
    public Object syncSheetState() throws Exception {
        return getClient().invoke("syncSheetState", "a", "b", "c", "d", "e");
    }

    @RequestMapping("query1/nms")
    @ResponseBody
    public Object query1Nms() throws Exception {
        return wsClientBussinessJaxb.sheetVerify();
    }

    @RequestMapping("query2/nms")
    @ResponseBody
    public Object query2Nms() throws Exception {
        return wsClientBussinessJaxb.sheetVerifyAndCallback();
    }

    @RequestMapping("query3/nms")
    @ResponseBody
    public Object query3Nms() throws Exception {
        return wsClientBussinessJaxb.syncSheetStateMethod();
    }
}
