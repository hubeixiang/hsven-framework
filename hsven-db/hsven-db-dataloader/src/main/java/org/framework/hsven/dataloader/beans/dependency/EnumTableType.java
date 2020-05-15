package org.framework.hsven.dataloader.beans.dependency;

public enum EnumTableType {
    Main, //主表
    Child, //直接子表,直接与主表关联的字表
    Lazy_subtable //间接子表,不是直接与主表关联的字表
}
