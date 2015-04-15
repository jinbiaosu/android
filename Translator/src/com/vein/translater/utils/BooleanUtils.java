package com.vein.translater.utils;

public class BooleanUtils {
	public static boolean isNull(String value) {
		value = value.trim();
		if (value == null || "".equals(value)) {
			return true;
		}

		return false;
	}

}
