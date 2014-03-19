package com.projects.demo.util;

import java.io.UnsupportedEncodingException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public final class PinYinUtils {

	private PinYinUtils() {
	}
	
	/**
	 * 判断一个字符是否是中文字符
	 */
	private static boolean isChineseCharacter(char c) {
		return String.valueOf(c).matches("[\\u4E00-\\u9FA5]+");
	}

	/**
	 * 将一个含有中文的字符串转换成拼音。
	 * Note： 这里只是将中文转换成拼音，其它的各种字符将保持原来的样子。
	 */
	public static String populatePinYing(String aChineseValue) {

		if (null == aChineseValue) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		char[] charArray = aChineseValue.toCharArray();

		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		for (int i = 0; i < charArray.length; i++) {
			if (isChineseCharacter(charArray[i])) {
				try {
					sb.append(PinyinHelper.toHanyuPinyinStringArray(
							charArray[i], outputFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				sb.append(charArray[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 将一个含有中文的字符串转换成拼音。
	 * Note： 这里只是将中文转换成拼音，其它的各种字符将保持原来的样子。
	 * 
	 * 在這裡多了一個參數needToCorrectSpelling,这个主要用于调整姓氏的发音。
	 * 如 '单'， 这个字作为姓氏念 shan, 但同时也有dan的发音等。
	 * 
	 * 为了保证姓氏发音的正确性，将了这个参数和相关的简单逻辑
	 */
	public static String populatePinYing(String aChineseValue,
			boolean needToCorrectSpelling) {

		if (null == aChineseValue) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		char[] charArray = aChineseValue.toCharArray();

		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		String surname = null;
		for (int i = 0; i < charArray.length; i++) {
			if (isChineseCharacter(charArray[i])) {
				try {
					if (needToCorrectSpelling && 0 == i) {
						surname = SurnameDictionary
								.populateCorrectSpelling(charArray[i]);
						if (null == surname) {
							sb.append(PinyinHelper.toHanyuPinyinStringArray(
									charArray[i], outputFormat)[0]);
						} else {
							sb.append(surname);
						}
					} else {
						sb.append(PinyinHelper.toHanyuPinyinStringArray(
								charArray[i], outputFormat)[0]);
					}

				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				sb.append(charArray[i]);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String x = "拼音4j%^**Cool12568 ｶ ｷ ";
		System.out.println(populatePinYing(x));
	}
}
