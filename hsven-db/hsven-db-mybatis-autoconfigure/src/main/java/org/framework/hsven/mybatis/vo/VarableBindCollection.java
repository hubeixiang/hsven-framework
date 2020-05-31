package org.framework.hsven.mybatis.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VarableBindCollection implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<VarableBind> columnList = new ArrayList<>();
    private Map<String, VarableBind> columnMap = new HashMap<>();

    public boolean containsVarableBind(VarableBind varableBind) {
        return columnMap.containsKey(varableBind.getColumn());
    }

    public boolean addVarableBind(VarableBind varableBind) {
        if (columnMap.containsKey(varableBind.getColumn())) {
            return false;
        } else {
            if (VarableBindConstants.testVarableBind(varableBind)) {
                columnMap.put(varableBind.getColumn(), varableBind);
                columnList.add(varableBind);
                return true;
            } else {
                return false;
            }
        }
    }

    public List<VarableBind> getColumnList() {
        return columnList;
    }
}
