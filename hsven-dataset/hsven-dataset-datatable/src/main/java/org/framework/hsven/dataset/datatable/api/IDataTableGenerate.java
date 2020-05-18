package org.framework.hsven.dataset.datatable.api;

import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;
import org.framework.hsven.dataset.datatable.cache.ColInfo;
import org.framework.hsven.dataset.datatable.cache.DataTable;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.util.List;

/**
 * 创建DataTable的接口api
 *
 * @param <E> 模型对象,获取相关的字段列表以及对应类型等等
 * @param <T> 字段类型映射到DataTable内部的对象
 */
public interface IDataTableGenerate<E, T> {


    /**
     * 依据外部的模型对象,创建对应的DataTable描述
     *
     * @param dataTableName
     * @param model
     * @return
     */
    public DataTable createDataTable(String dataTableName, E model);

    /**
     * 类型映射
     *
     * @param otherDataTypeEnum 外部的字段类型映射
     * @return 返回DataTablene内部的对象
     */
    public DataTypeEnum convert2DataTypeEnum(T otherDataTypeEnum);


    default ColInfo internalCreateColInfo(String name, String title, T enumType, String format) {
        try {
            DataTypeEnum type = convert2DataTypeEnum(enumType);
            if (type == null) {
                throw new DataSetException(String.format("DataTypeEnum can't null,origin enumType=[%s]", enumType));
            }

            ColInfo info = new ColInfo(name, title, type, format);
            return info;
        } catch (Exception e) {
            throw new DataSetException("DataTableGenerate.createColInfo Exception,name:" + name, e);
        }
    }

    default DataTable internalCreateDateTable(String dataTableName, List<ColInfo> colInfos) {
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
