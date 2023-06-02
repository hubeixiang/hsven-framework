package org.framework.hsven.commons.swagger2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnMissingBean(SwaggerConfiguration.class)
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Value("${swagger.rest.package:com}")
    private String restPackage;
    @Value("${swagger.rest.title:Restful接口描述}")
    private String restTitle;
    @Value("${swagger.rest.desc:Java后台服务提供的Restful接口}")
    private String restDesc;
    @Value("${swagger.rest.version:1.0.0}")
    private String restVersion;
    @Value("${swagger.contact.name:hsven}")
    private String contactName;
    @Value("${swagger.contact.url:www.hsven.com}")
    private String contactUrl;
    @Value("${swagger.contact.email:hubeixiang@aliyun.com}")
    private String contactEmail;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(restPackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(contactName, contactUrl, contactEmail);
        return new ApiInfoBuilder()
                .title(restTitle)
                .description(restDesc)
                .contact(contact)   // 联系方式
                .version(restVersion)  // 版本
                .build();
    }

    @ConditionalOnProperty(prefix = "swagger.springfox.fixed", name = "enable", havingValue = "true", matchIfMissing = true)
    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider
                        || bean instanceof WebFluxRequestHandlerProvider) {
                    customRequestMappingHandlerMapping(getHandlerMapping(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customRequestMappingHandlerMapping(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMapping(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);

                }
            }
        };
    }
}
