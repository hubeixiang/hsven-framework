# hsven-commons-i18n

使用spring-boot实现的国际化资源类集合

#org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration
使用时在application.properties中配置要解析加载的properties文件
#配置的路径+文件名,必须是完整的,只缺少文件后缀".properties"
spring.messages.basename=i18n/example_tips
basename后面的如果存在多个路径,使用逗号","进行分割
到时候默认加载classpath*:i18n/example_tips_(locale:zh_CN).properties文件

使用如下配置,配置默认的语言资源
hsven.locale.language=zh
hsven.locale.country=CN
