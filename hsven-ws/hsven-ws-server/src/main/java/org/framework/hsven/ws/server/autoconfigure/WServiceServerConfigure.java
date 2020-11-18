package org.framework.hsven.ws.server.autoconfigure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 *
 */
@EnableWs
@Configuration
public class WServiceServerConfigure {

    //定义的不同的xsd服务文件中的命名空间
    //要与xsd文件中的命名空间一致
    public static final String NAMESPACE_URI = "http://wservice.hios.com/proxy/eoms/service";

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /**
     * 依据定义的xsd文件,发布
     *
     * @param xsdSchema
     * @return
     */
    @Bean(name = "eoms")
    public DefaultWsdl11Definition defaultWsdl11Definition(@Qualifier("eomsSchema") XsdSchema xsdSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        //设置具体的bean名称
        wsdl11Definition.setPortTypeName("EomsPort");
        //对外访问的Web Service服务的路径
        //最终组成的路径是   http://127.0.0.1:38620/${本Web服务的名称}/ws(此处设置的setLocationUri地址)/
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
        wsdl11Definition.setSchema(xsdSchema);
        return wsdl11Definition;
    }

    /**
     * 导入具体定义的xsd文件
     * 如果有多个xsd服务需要发布,就生成多个XsdSchema,取不同的bean名称
     *
     * @return
     */
    @Bean
    public XsdSchema eomsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/eoms.xsd"));
    }
}
