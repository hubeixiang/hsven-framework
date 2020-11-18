package org.framework.hsven.ws.client.controller;

import org.framework.hsven.ws.client.service.client.WsClientBussinessEoms;
import org.framework.hsven.ws.client.service.client.WsClientBussinessOne;
import org.framework.hsven.ws.client.service.client.WsClientBussinessSecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wellcom")
public class WellcomController {

    @Autowired
    WsClientBussinessOne wsClientBussinessOne;


    @Autowired
    WsClientBussinessSecond wsClientBussinessSecond;

    @Autowired
    WsClientBussinessEoms wsClientBussinessEoms;

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
}
