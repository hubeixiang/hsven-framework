# Quartz操作模块

## 引用Quartz三方包

1. quartz  必须的
>>
			<dependency>
				<groupId>org.quartz-scheduler</groupId>
				<artifactId>quartz</artifactId>
				<version>2.2.1</version>
			</dependency>

2. quartz-jobs 不知道是否需要
>>
            <dependency>
                <groupId>org.quartz-scheduler</groupId>
                <artifactId>quartz-jobs</artifactId>
                <version>2.2.1</version>
            </dependency>

## Quartz操作相关API

1. 简单任务调度
2. 集群的任务调度执行
