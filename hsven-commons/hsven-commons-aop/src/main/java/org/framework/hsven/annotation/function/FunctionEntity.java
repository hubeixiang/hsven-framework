package org.framework.hsven.annotation.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FunctionEntity implements Serializable {
    private static final long serialVersionUID = 42L;
    private List<FunctionValue> data = new ArrayList<>();

    public List<FunctionValue> getData() {
        return data;
    }

    public void setData(List<FunctionValue> data) {
        this.data = data;
    }
}
