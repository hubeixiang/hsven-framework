package org.framework.hsven.service.dao.api;

import org.framework.hsven.file.location.FileLocation;
import org.framework.hsven.service.dao.condition.ExportExcelCondition;

import java.util.List;
import java.util.function.Supplier;

/**
 * @description:
 * @author: sven
 * @create: 2021-10-11
 **/

public interface ICommonData2File {

    /**
     * 将提供的数据,一次性输出到文件中
     *
     * @param exportExcelCondition 导出参数
     * @param supplier             生成文件的对象数组
     * @param <E>                  对象类型
     * @return 文件
     */
    public <E> FileLocation export2FileLocation(ExportExcelCondition exportExcelCondition, Supplier<List<E>> supplier);

}
