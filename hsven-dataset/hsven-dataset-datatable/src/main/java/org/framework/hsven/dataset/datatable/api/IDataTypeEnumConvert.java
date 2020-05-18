package org.framework.hsven.dataset.datatable.api;

import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;

public interface IDataTypeEnumConvert<T> {
    public DataTypeEnum convert2DataTypeEnum(T otherDataTypeEnum);
}
