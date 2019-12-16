#待执行任务调度模块(hsven-dispatch-tasks)

---

[TOC]

---

## 任务调度API

### IDispatchTasksService
任务调度的api,本模块中已经实现了任务调度的流程

>> api说明
>>* 实现类是 :  AbstractDispatchTasksService
>>* 此类是抽象类,还需要各自实现其相关的3个抽象接口
>>* 三个抽象接口分别功能是
>>1. getDispatchProperties() 获取派发相关的属性
>>2. getIDispatchTaskOperation() 获取待执行任务列表操作接口
>>3. getIDispatchTaskExecute() 获取调度每一个待执行任务的接口


### IDispatchTaskOperation
待执行任务列表获取操作接口，其中也包括操作待执行任务的相关接口

>> api说明
>> 具体的接口功能说明参见接口定义

### IDispatchTaskExecute
其中主要的功能是调用具体的业务实现服务

>> api 说明
>> 具体的接口功能参见接口定义





