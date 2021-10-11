package org.framework.hsven.service.core.support.collection;

public class ResultVOO<E> implements ResultVO {
    private E data;

    public static <E> ResultVOO of() {
        return new ResultVOO<E>();
    }

    public static <E> ResultVOO of(E data) {
        ResultVOO<E> resultVOEntity = new ResultVOO<E>();
        resultVOEntity.setData(data);
        return resultVOEntity;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
