package org.framework.hsven.cxfws.server.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * @description: ws服务, 请求头添加鉴权信息
 * @author: sven
 * @create: 2021-09-02
 **/

public class WSAuthHandler implements SOAPHandler<SOAPMessageContext> {
    private Logger log = LoggerFactory.getLogger(WSAuthHandler.class);

    private String AUTH_PWD = "123";
    private String AUTH_NAME = "admin";
    private String AUTH_URI = "http://service.springboot.dreamboat.com.cn/auth";

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        // 判断消息是请求还是响应
        Boolean output = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = false;
        SOAPMessage message = context.getMessage();

        //如果是请求，则执行校验
        try {
            if (!output) {
                result = validate(message);

                if (!result) {
                    validateFail(message);
                }
            }

            message.writeTo(System.out);
        } catch (SOAPException | IOException e) {
            log.error("鉴权验证失败：{}", e.getMessage(), e);
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 授权校验失败，在SOAPBody中添加SOAPFault
     *
     * @param message
     */
    private void validateFail(SOAPMessage message) throws SOAPException {
        SOAPEnvelope envelop = message.getSOAPPart().getEnvelope();

        envelop.getHeader().detachNode();
        envelop.addHeader();

        envelop.getBody().detachNode();
        SOAPBody body = envelop.addBody();

        SOAPFault fault = body.getFault();

        if (fault == null) {
            fault = body.addFault();
        }

        fault.setFaultString("授权校验失败！");

        message.saveChanges();
    }

    /**
     * 授权校验
     *
     * @param message
     * @return 校验成功返回true，校验失败返回false
     */
    private boolean validate(SOAPMessage message) throws SOAPException {
        boolean result = false;

        SOAPEnvelope envelop = message.getSOAPPart().getEnvelope();
        SOAPHeader header = envelop.getHeader();

        if (header != null) {
            Iterator iterator = header.getChildElements(new QName(AUTH_URI, "auth"));
            SOAPElement auth;

            if (iterator.hasNext()) {
                //获取auth
                auth = (SOAPElement) iterator.next();

                //获取name
                Iterator it = auth.getChildElements(new QName(AUTH_URI, "name"));
                SOAPElement name = null;
                if (it.hasNext()) {
                    name = (SOAPElement) it.next();
                }

                //获取password
                it = auth.getChildElements(new QName(AUTH_URI, "password"));
                SOAPElement password = null;
                if (it.hasNext()) {
                    password = (SOAPElement) it.next();
                }

                //判断name和password是否符合要求
                if (name != null && password != null && AUTH_NAME.equals(name.getValue().trim()) && AUTH_PWD.equals(password.getValue().trim())) {
                    result = true;
                }
            }
        }

        return result;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
