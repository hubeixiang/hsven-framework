package org.framework.hsven.dataset.datatable.api.impl;

import org.framework.hsven.dataset.datatable.api.IDataTypeEnumConvert;
import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;

public class DefaultDataTypeEnumConvert implements IDataTypeEnumConvert<DataTypeEnum> {
    @Override
    public DataTypeEnum convert2DataTypeEnum(DataTypeEnum otherDataTypeEnum) {
        return otherDataTypeEnum;
    }
}
