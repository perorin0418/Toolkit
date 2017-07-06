package org.net.perorin.toolkit;

public class StringUtils {

	public static String substringFromBack(String str, int count) {
		str = str.substring(str.length() - count, str.length());
		return str;
	}

}
