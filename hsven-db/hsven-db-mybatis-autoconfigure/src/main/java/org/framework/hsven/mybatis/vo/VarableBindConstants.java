package org.framework.hsven.mybatis.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author sven
 * @date 2019/7/8 11:02
 */
public class VarableBindConstants {
    //1: = 直接等于的方式 使用预编译的方式
    //2: = 直接等于的方式 但是不使用预编译的方式
    //3: like 使用模糊匹配的方式
    //4: in (list) 的方式,此时绑定的变量是数组
    //5: > 使用预编译的方式,绑定大于操作符
    //6: >= 使用预编译的方式,绑定大于等于操作符
    //7: < 使用预编译的方式,绑定小于操作符
    //8: <= 使用预编译的方式,绑定小于等于操作符
    public final static int bindType_EQUAL_PRECOMPILE = 1;
    public final static int bindType_EQUAL_APPEND = 2;
    public final static int bindType_LIKE = 3;
    public final static int bindType_List = 4;
    public final static int bindType_gt = 5;
    public final static int bindType_gt_eq = 6;
    public final static int bindType_lt = 7;
    public final static int bindType_lt_eq = 8;
    public final static int[] bindTypes = new int[]{};

    public final static boolean testVarableBind(VarableBind varableBind) {
        if (varableBind == null) {
            return false;
        }
        if (StringUtils.isEmpty(varableBind.getColumn())) {
            return false;
        }

        switch (varableBind.getBindType()) {
            case bindType_EQUAL_PRECOMPILE:
            case bindType_EQUAL_APPEND:
            case bindType_LIKE:
            case bindType_gt:
            case bindType_gt_eq:
            case bindType_lt:
            case bindType_lt_eq:
                return true;
            case bindType_List:
                Object object = varableBind.getValue();
                if (object != null && object instanceof java.util.Collection) {
                    Collection collection = (Collection) object;
                    if (collection.size() == 0) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
}
