package org.framework.hsven.ws.client.controller;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @description:
 * @author: sven
 * @create: 2021-09-02
 **/

public class ESBHeaderIntercepter extends AbstractSoapInterceptor {

    public ESBHeaderIntercepter() {
        super(Phase.WRITE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        Document doc = DOMUtils.createDocument();
        // 根节点 EsbInfo
        Element rootEle = doc.createElementNS("", "EsbInfo");
        // AppID
        Element appID = doc.createElement("AppID");
        appID.setTextContent("Fuck");
        rootEle.appendChild(appID);
        // 密码
        Element provide_AppID = doc.createElement("Provide_AppID");
        provide_AppID.setTextContent("我擦");
        rootEle.appendChild(provide_AppID);
        // 添加到头
        List<Header> headers = soapMessage.getHeaders();
        QName qname = new QName("EsbInfo");
        SoapHeader head = new SoapHeader(qname, rootEle);
        headers.add(head);
    }
}
