package org.framework.hsven.mybatis.vo;

import java.util.HashMap;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class UpperCaseResultMap<V> extends HashMap<String, V> {
	private static final long serialVersionUID = 3L;

	@Override
	public V put(String key, V value) {
		if (key == null || key.trim().length() == 0) {
			throw new IllegalArgumentException("Empty key is not allowed.");
		}

		key = key.toUpperCase();

		return super.put(key, value);
	}
}
