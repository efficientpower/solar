package org.wjh.solar.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author wangjihui
 *
 */
public class RegExpUtils {

	public static boolean isMatcher(String regExp, String source) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static Matcher getMatcher(String regExp, String source) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(source);
		matcher.find();
		return matcher;
	}
}
