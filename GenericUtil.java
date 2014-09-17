package org.zen.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericUtil {
	public static <TKey, TValueKey, TValue> HashMap<TValueKey, TValue> getValueMap(
			HashMap<TKey, HashMap<TValueKey, TValue>> map, TKey tk) {
		HashMap<TValueKey, TValue> tv = map.get(tk);
		if (tv == null) {
			tv = new HashMap<TValueKey, TValue>();
			map.put(tk, tv);
		}
		return tv;
	}

	public static <TKey, TValue> ArrayList<TValue> getValueList(HashMap<TKey, ArrayList<TValue>> map, TKey tk) {
		ArrayList<TValue> tv = map.get(tk);
		if (tv == null) {
			tv = new ArrayList<TValue>();
			map.put(tk, tv);
		}
		return tv;
	}

	public static <E> String toString(Iterable<E> collection) {
		return toString(collection, ",");
	}

	public static <E> String toString(Iterable<E> collection, String delimiter) {
		if (collection == null)
			return null;

		StringBuilder buffer = new StringBuilder();
		for (E element : collection) {
			if (buffer.length() > 0)
				buffer.append(delimiter);
			buffer.append(element);
		}

		return buffer.toString();
	}

	public static <E> String toString(E[] collection) {
		return toString(collection, ",");
	}

	public static <E> String toString(E[] collection, String delimiter) {
		if (collection == null)
			return null;

		StringBuilder buffer = new StringBuilder();
		for (E element : collection) {
			if (buffer.length() > 0)
				buffer.append(delimiter);
			buffer.append(element);
		}

		return buffer.toString();
	}
}
