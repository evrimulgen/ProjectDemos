package com.projects.demo.util;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/*******************************************************************************
 * pinyin4j is a plug-in, you can kind of Chinese characters into phonetic.Multi-tone character,Tone
 * Detailed view http://pinyin4j.sourceforge.net/
 * 
 * @author Administrator
 * @ClassName: Pinyin4jUtil
 * @Description: TODO
 * @author wang_china@foxmail.com
 * @date Jan 13, 2012 9:28:28 AM
 */
public class Pinyin4jUtil {

	/***************************************************************************
	 * ��ȡ���ĺ���ƴ�� Ĭ�����
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:54:01 AM
	 * @param chinese
	 * @return
	 */
	public static String getPinyin(String chinese) {
		return getPinyinZh_CN(makeStringByStringSet(chinese));
	}

	/***************************************************************************
	 * ƴ����д���
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:58:45 AM
	 * @param chinese
	 * @return
	 */
	public static String getPinyinToUpperCase(String chinese) {
		return getPinyinZh_CN(makeStringByStringSet(chinese)).toUpperCase();
	}

	/***************************************************************************
	 * ƴ��Сд���
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:58:45 AM
	 * @param chinese
	 * @return
	 */
	public static String getPinyinToLowerCase(String chinese) {
		return getPinyinZh_CN(makeStringByStringSet(chinese)).toLowerCase();
	}

	/***************************************************************************
	 * ����ĸ��д���
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 10:00:54 AM
	 * @param chinese
	 * @return
	 */
	public static String getPinyinFirstToUpperCase(String chinese) {
		return getPinyin(chinese);
	}

	/***************************************************************************
	 * ƴ����ƴ���
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 11:08:15 AM
	 * @param chinese
	 * @return
	 */
	public static String getPinyinJianPin(String chinese) {
		return getPinyinConvertJianPin(getPinyin(chinese));
	}

	/***************************************************************************
	 * �ַ���ת��
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:34:11 AM
	 * @param chinese
	 *            ���ĺ���
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static Set<String> makeStringByStringSet(String chinese) {
		char[] chars = chinese.toCharArray();
		if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
			char[] srcChar = chinese.toCharArray();
			String[][] temp = new String[chinese.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];

				// �����Ļ���a-z����A-Zת��ƴ��
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {

					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(
								chars[i], getDefaultOutputFormat());

					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else if (((int) c >= 65 && (int) c <= 90)
						|| ((int) c >= 97 && (int) c <= 122)) {
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else {
					temp[i] = new String[] { "" };
				}
			}
			String[] pingyinArray = Exchange(temp);
			Set<String> zhongWenPinYin = new HashSet<String>();
			for (int i = 0; i < pingyinArray.length; i++) {
				zhongWenPinYin.add(pingyinArray[i]);
			}
			return zhongWenPinYin;
		}
		return null;
	}

	/***************************************************************************
	 * Default Format Ĭ�������ʽ
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:35:51 AM
	 * @return
	 */
	public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// Сд
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// û����������
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u��ʾ
		return format;
	}

	/***************************************************************************
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:39:54 AM
	 * @param strJaggedArray
	 * @return
	 */
	public static String[] Exchange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	/***************************************************************************
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:39:47 AM
	 * @param strJaggedArray
	 * @return
	 */
	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = capitalize(strJaggedArray[0][i])
							+ capitalize(strJaggedArray[1][j]);
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	/***************************************************************************
	 * ����ĸ��д
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:36:18 AM
	 * @param s
	 * @return
	 */
	public static String capitalize(String s) {
		char ch[];
		ch = s.toCharArray();
		if (ch!= null && ch.length > 0) {
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				ch[0] = (char) (ch[0] - 32);
			}
		}
		String newString = new String(ch);
		return newString;
	}

	/***************************************************************************
	 * �ַ�������ת���ַ���(���ŷָ�)
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:37:57 AM
	 * @param stringSet
	 * @return
	 */
	public static String getPinyinZh_CN(Set<String> stringSet) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
				str.append(s + ",");
			}
			i++;
		}
		return str.toString();
	}

	/***************************************************************************
	 * ��ȡÿ��ƴ���ļ��
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 11:05:58 AM
	 * @param chinese
	 * @return
	 */
	public static String getPinyinConvertJianPin(String chinese) {
		String[] strArray = chinese.split(",");
		String strChar = "";
		for (String str : strArray) {
			char arr[] = str.toCharArray(); // ���ַ���ת����char������
			for (int i = 0; i < arr.length; i++) {
//				if (arr[i] >= 65 && arr[i] < 91) { // �ж��Ƿ��Ǵ�д��ĸ original
				if (arr[i] >= 65 && arr[i] < 91 || (arr[i] >= 48 && arr[i] <= 57)) { // �ж��Ƿ��Ǵ�д��ĸ
					strChar += new String(arr[i] + "");
				}
			}
			strChar += ",";
		}
		return strChar;
	}

	/***************************************************************************
	 * Test
	 * 
	 * @Name: Pinyin4jUtil.java
	 * @Description: TODO
	 * @author: wang_chian@foxmail.com
	 * @version: Jan 13, 2012 9:49:27 AM
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "3����3����";
		System.out.println("Сд�����" + getPinyinToLowerCase(str));
		System.out.println("��д�����" + getPinyinToUpperCase(str));
		System.out.println("����ĸ��д�����" + getPinyinFirstToUpperCase(str));
		System.out.println("��ƴ�����" + getPinyinJianPin(str));

	}
}

