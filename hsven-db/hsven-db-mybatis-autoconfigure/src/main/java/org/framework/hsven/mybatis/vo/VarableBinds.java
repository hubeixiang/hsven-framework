package org.framework.hsven.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VarableBinds {
    private List<VarableBind> columnList = new ArrayList<>();

    public List<VarableBind> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<VarableBind> columnList) {
        this.columnList = columnList;
    }
}
