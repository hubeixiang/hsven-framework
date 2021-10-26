package org.framework.hsven.service.dao.pk;

import java.io.Serializable;

/**
 * @description: 二维主键
 * @author: sven
 * @create: 2021-10-16
 **/

public class TwoDimensional<E1, E2> implements Serializable {
    private E1 dim1;
    private E2 dim2;

    public E1 getDim1() {
        return dim1;
    }

    public void setDim1(E1 dim1) {
        this.dim1 = dim1;
    }

    public E2 getDim2() {
        return dim2;
    }

    public void setDim2(E2 dim2) {
        this.dim2 = dim2;
    }
}
