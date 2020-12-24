package org.framework.hsven.ws.client.controller;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.framework.hsven.ws.client.service.client.WsClientBussiness4A;
import org.framework.hsven.ws.client.service.client.WsClientBussinessEoms;
import org.framework.hsven.ws.client.service.client.WsClientBussinessOne;
import org.framework.hsven.ws.client.service.client.WsClientBussinessSecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("welcome")
public class WellcomController {

    @Autowired
    WsClientBussinessOne wsClientBussinessOne;


    @Autowired
    WsClientBussinessSecond wsClientBussinessSecond;

    @Autowired
    WsClientBussinessEoms wsClientBussinessEoms;

    @Autowired
    WsClientBussiness4A wsClientBussiness4A;

    @RequestMapping("one")
    @ResponseBody
    public Object requestOne() {
        return wsClientBussinessOne.getUrl();
    }

    @RequestMapping("second")
    @ResponseBody
    public Object requestSecond() {
        return wsClientBussinessSecond.getUrl();
    }


    @RequestMapping("secondsend")
    @ResponseBody
    public Object requestSecondSend() {
        return wsClientBussinessSecond.createWsObject();
    }

    @RequestMapping("checkImport")
    @ResponseBody
    public Object checkImport() {
        return wsClientBussinessEoms.checkImport();
    }

    @RequestMapping("quitOrderUpdate")
    @ResponseBody
    public Object quitOrderUpdate() {
        return wsClientBussinessEoms.quitOrderUpdate();
    }

    @RequestMapping("4a-getUserAmount")
    @ResponseBody
    public Object getUserAmount() {
        return wsClientBussiness4A.getUserAmount();
    }

    @RequestMapping("4a-findUser")
    @ResponseBody
    public Object findUser() {
        return wsClientBussiness4A.findUser();
    }

    @RequestMapping("4a-getUserAmount-test")
    @ResponseBody
    public Object getUserAmountTest() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://127.0.0.1:17027/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://192.168.1.21:17027/ws/userManager?wsdl");
        Client client = dcf.createClient("http://10.110.58.154:8888/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.154:8888/gateway/platform-security-sendlog/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.154:8888/sendlog_service/?wsdl");
//        Client client = dcf.createClient("http://10.110.58.147:17103/platform-security-sendlog/ws/userManager?wsdl");
        return client.invoke("queryUsers");
    }

    @RequestMapping("4a-findUser-test")
    @ResponseBody
    public Object findUserTest() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://127.0.0.1:17027/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.151:8888/ws/userManager?wsdl");
        Client client = dcf.createClient("http://10.110.58.154:8888/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://192.168.1.21:17027/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.154:8888/gateway/platform-security-sendlog/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.154:8888/sendlog_service/?wsdl");
        return client.invoke("findUser", "admin");
    }

    @RequestMapping("xxxx")
    @ResponseBody
    public Object xssss() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://10.110.58.154:8888/e2e-iss-ws/services/IEomsStateSyncService?wsdl");
        Client client = dcf.createClient("http://10.110.58.151:8888/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://192.168.1.21:17027/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.154:8888/gateway/platform-security-sendlog/ws/userManager?wsdl");
//        Client client = dcf.createClient("http://10.110.58.154:8888/sendlog_service/?wsdl");
        return client.invoke("syncSheetState", "admin","1","2","3","4");
    }
}
