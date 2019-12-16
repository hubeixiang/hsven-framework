package org.framework.hsven.mybatis.vo;

import java.util.HashMap;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class ResultMap<K, V> extends HashMap<K, V> {
	private static final long serialVersionUID = 1L;

	public V put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Empty key is not allowed.");
		}

		if (key instanceof String) {
			//noinspection unchecked
			key = (K) ((String) key).toUpperCase();
		}

		return super.put(key, value);
	}
}
