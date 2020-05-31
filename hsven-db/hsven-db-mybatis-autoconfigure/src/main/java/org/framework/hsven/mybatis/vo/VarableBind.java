package org.framework.hsven.mybatis.vo;

import java.io.Serializable;

public class VarableBind<E> implements Serializable {
    private static final long serialVersionUID = 1L;
    //值来源于VarableBindConstants中的常量
    private int bindType = 1;
    private String column;
    //E有可能是Object,但是当bindType=VarableBindConstants.bindType_List时,绑定的是List集合数组变量
    private E value;

    public int getBindType() {
        return bindType;
    }

    public void setBindType(int bindType) {
        this.bindType = bindType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
