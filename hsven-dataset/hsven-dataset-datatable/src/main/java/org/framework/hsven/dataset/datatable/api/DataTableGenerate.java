package org.framework.hsven.dataset.datatable.api;

import org.framework.hsven.dataset.datatable.api.impl.DefaultDataTypeEnumConvert;
import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;
import org.framework.hsven.dataset.datatable.cache.ColInfo;
import org.framework.hsven.dataset.datatable.cache.DataTable;
import org.framework.hsven.dataset.datatable.exception.DataSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 对外提供的DataTable创建工具类
 */
public class DataTableGenerate {
    public final static IDataTypeEnumConvert DefaultDataTypeEnumConvert = new DefaultDataTypeEnumConvert();
    private static Logger logger = LoggerFactory.getLogger(DataTableGenerate.class);

    public final static ColInfo createColInfo(IDataTypeEnumConvert iDataTypeEnumConvert, String name, String title, Enum enumType, String format) {
        if (iDataTypeEnumConvert == null) {
            iDataTypeEnumConvert = DefaultDataTypeEnumConvert;
        }

        try {
            DataTypeEnum type = iDataTypeEnumConvert.convert2DataTypeEnum(enumType);
            if (type == null) {
                throw new DataSetException(String.format("DataTypeEnum can't null,origin enumType=[%s]", enumType));
            }

            ColInfo info = new ColInfo(name, title, type, format);
            return info;
        } catch (Exception e) {
            logger.error("DataTableGenerate.createColInfo Exception,name:" + name, e);
        }
        return null;
    }

    public final static DataTable createDateTable(String dataTableName, List<ColInfo> colInfos) {
        DataTable dataTable = new DataTable(dataTableName);
        int fieldListSize = 0;
        if (colInfos != null) {
            fieldListSize = colInfos.size();
        }
        for (int i = 0; i < fieldListSize; i++) {
            ColInfo colInfo = colInfos.get(i);
            dataTable.addCol(colInfo);
        }

        dataTable.init();
        return dataTable;
    }
}
