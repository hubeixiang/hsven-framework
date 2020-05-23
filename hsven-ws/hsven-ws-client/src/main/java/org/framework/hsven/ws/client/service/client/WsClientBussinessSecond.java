package org.framework.hsven.ws.client.service.client;

import com.boco.webservice.NewSMSInterface;
import com.boco.webservice.NewSMSInterfaceResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * 第二个ws客户端访问处理
 */
public class WsClientBussinessSecond extends WebServiceGatewaySupport {
    public String getUrl() {
        return getWebServiceTemplate().getDefaultUri();
    }

    public String createWsObject(){
        String content = "<xml><Item><EventId><![CDATA[2810915697000012832]]></EventId><EventType><![CDATA[1060310]]></EventType><AEMS><![CDATA[DAC-U2000-1-P]]></AEMS><AENDME><![CDATA[13-7842-龙泉扇坡村]]></AENDME><AMEALIAS><![CDATA[13-7842-龙泉扇坡村;板卡:5-D2EX1;端口:1]]></AMEALIAS><TITLE><![CDATA[ETH_LOS]]></TITLE><ALARMID><![CDATA[1234115656434364]]></ALARMID><ZEMS><![CDATA[DAC-U2000-1-P]]></ZEMS><ZENDME><![CDATA[13-7841-黄连村大内口梁上-新建]]></ZENDME><ZMEALIAS><![CDATA[13-7841-黄连村大内口梁上-新建;板卡:6-D2EX1;端口:1]]></ZMEALIAS><ASITES><![CDATA[达州宣汉龙泉扇坡村]]></ASITES><ZSITES><![CDATA[达州宣汉龙泉乡黄连村大内口梁上]]></ZSITES><MessageState><![CDATA[0]]></MessageState><MessageContent><![CDATA[达州PTN超大环传输预警报告：2019-11-18 16:58:41,达州本地网宣汉县13-7842-龙泉扇坡村__宣汉县13-7841-黄连村大内口梁上-新建中断,该环上承载了58个基站业务，7个集客专线业务，形成环上大面积断站隐患，请达州分公司及时处理故障。]]></MessageContent><EventTime><![CDATA[2019-11-18 16:59:23]]></EventTime><RegionName><![CDATA[达州]]></RegionName><Num1><![CDATA[58]]></Num1><Operator><![CDATA[]]></Operator><Operatetime><![CDATA[]]></Operatetime><Content><![CDATA[宣汉县13-7842-龙泉扇坡村__宣汉县13-7841-黄连村大内口梁上-新建(10GE)]]></Content><ReferArea><![CDATA[]]></ReferArea></Item></xml>";
        NewSMSInterface newSMSInterface = new NewSMSInterface();
        newSMSInterface.setIn0(content);

        NewSMSInterfaceResponse response = (NewSMSInterfaceResponse) getWebServiceTemplate().marshalSendAndReceive(newSMSInterface, new SoapActionCallback("newSMSInterface"));
        if (response == null) {
            logger.error("newSMSInterface,return[NewSMSInterfaceResponse is null]");
            throw new RuntimeException("ws newSMSInterface response is null");
        }
        String out = response.getOut();
        return out;
    }
}
