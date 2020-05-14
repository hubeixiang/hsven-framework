# Zookeeper操作模块

## 引用的zookeeper三方包是

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

## Zookeeper操作相关API

1. 保存、新增、删除
2. 事务操作
3. 重连操作
4. 选举操作
5. 监听操作
