package org.framework.hsven.service.core.support.collection;

import java.util.HashMap;

public class ResultMap<V> extends HashMap<String, V> {

    public static ResultMap of() {
        return new ResultMap();
    }

}
