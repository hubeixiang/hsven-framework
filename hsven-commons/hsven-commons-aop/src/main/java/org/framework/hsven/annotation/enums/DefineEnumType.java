package org.framework.hsven.annotation.enums;

/**
 * Created by sven on 2017/3/4.
 * 资源管理中使用到的枚举类型定义
 */
public class DefineEnumType {
    //定义资源业务数据约束类型时使用,同时也是页面呈现该属性时布局类型的枚举
    //纯输入框 textBox, 时间输入框 date, 检索文本框 textRetrieval, 下拉框 selectOption;
    @EnumClassAnnotation(group = FieldDisplayEnumType.class, groupName = "页面展现框",
            enumValues = "{\"data\":[{\"value\":\"textBox\",\"name\":\"纯输入框\"}]}")
    public enum FieldDisplayEnumType {
        textBox,
        date,
        textRetrieval,
        selectOption
    }

    //设置校验时没有校验、或者校验失败时的取值方式
    public enum ValuePolicyEnumType {
        //常量获取方式
        constants,
        //获取模板中指定字段的值
        template
    }

    //资源映射关系类型枚举
    public enum RelatedEnumType {
        //1对1
        r1t1,
        //1对多
        r1tm,
        //多对1
        rmt1,
        //多对多
        rmtm
    }

    public enum TreeEnumType {
        //关联资源是当前资源的父资源,操作子资源,不影响父资源,但是删除父资源,强制删除子资源.更新父资源属性,需要更新子资源相关联属性
        father,
        //关联资源是当前资源的子资源,操作子资源,不影响父资源,但是删除父资源,强制删除子资源.更新父资源属性,需要更新子资源相关联属性
        son,
        //关联资源是当前资源的父资源,操作子资源,不影响父资源,但是删除父资源,强制更新关联子资源的关联属性为null.更新父资源属性,需要更新子资源相关联属性
        father_parallel,
        //关联资源是当前资源的子资源,操作子资源,不影响父资源,但是删除父资源,强制更新关联资资源的关联属性为null.更新父资源属性,需要更新子资源相关联属性
        son_parallel,
        //关联资源与当前资源是平级关系(资源之间直接关联),没有强关联关系,删除其中一种资源,另外一种资源不删除,只更新其关联属性.更新一种资源属性,需要更新另一种资源相关联属性
        parallel,
        //表明只是有关联关系,方便关联查询,但是并不进行任何关联数据增删改操作
        none
    }

    public enum QueryType {
        //默认支持的进程内缓存
        defaultcache,
        //暂时不支持的redis缓存
        redis,
        //不做缓存，实时从数据库中查询
        database
    }

    public enum ResourceOptCode {
        //允许删除
        DEL,
        //允许新增
        ADD,
        //允许修改
        UPT
    }

    public enum AttachmentType {
        //附件进行数据库存储
        database,
        //附件进行本地磁盘存储
        localdisk,
        //附件进行hdfs磁盘存储
        hdfs
    }

    //验证过程中,未提供数据主键时如何关联数据库中数据
    public enum NotProvidedDataPrimaryRelatedProcessType {
        //必须使用数据主键进行查询数据关联(默认方式),此时需要先生成数据主键
        query_data_primary,
        //暂时不支持这种方式
        //必须使用业务主键进行查询数据关联,需要在配置文件中配置提供配置的业务主键
        //(如果关联上,此时需要对比生成的数据主键和查询出来的数据主键,此时如果更新需要依据ProvidedDataPrimaryUpdateProcessType的设置进行更新)
        query_business_primary
    }

    //验证过程中,提供了数据主键时,做更新操作时,更新字段的方式
    public enum ProvidedDataPrimaryUpdateProcessType {
        //依据主键更新属性传入的属性以及被影响的字段
        //数据主键值与生成数据主键的值必须保持一致(默认方式)
        must_correspondence,
        //数据主键值与生成数据主键的值,不用保持一致
        not_correspondence
    }

    //特殊格式的值格式验证
    public enum FormatType {
        //验证是数字类型
        Decimal,
        //验证是整数数字类型
        DecimalInteger,
        //验证是yyyy-mm-dd格式
        DateMonth
    }
}
