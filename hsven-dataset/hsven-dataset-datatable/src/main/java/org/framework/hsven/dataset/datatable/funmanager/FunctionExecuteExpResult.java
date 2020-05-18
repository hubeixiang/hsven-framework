package org.framework.hsven.dataset.datatable.funmanager;

import java.util.ArrayList;
import java.util.List;

public class FunctionExecuteExpResult {
    public String executableExpString = null;                                //可执行表达式
    public String rawExpString = null;                                    //原始的raw表达式
    public List<String> newQueryObjNameList = new ArrayList<String>();    //需要 new的QueryObj对象名称列表
    public boolean isIdempotent = true;                                    //是否幂等性

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("raw:").append(rawExpString).append("\r\n");
        sb.append("exe:").append(executableExpString).append("\r\n");
        sb.append("newObjList:\r\n");
        for (int jj = newQueryObjNameList.size() - 1; jj >= 0; jj--) {
            sb.append(newQueryObjNameList.get(jj)).append("\r\n");
        }
        sb.append("Idempotent:").append(isIdempotent);
        return sb.toString();
    }
}
