# mybatis 依据数据库自动生成mapper配置文件以及接口

## 引用的spring,mybatis,spring-mybatis

1. org.apache.zookeeper
>>
        <dependency>
            <groupId>com.101tec</groupId>
            <artifactId>zkclient</artifactId>
            <version>0.8</version>
            <scope>compile</scope>
        </dependency>
        
2. com.101tec
>>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.6</version>
            <exclusions>
                <exclusion>
                    <artifactId>slf4j-log4j12</artifactId>
                    <groupId>org.slf4j</groupId>
                </exclusion>
            </exclusions>
        </dependency>


