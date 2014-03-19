package com.projects.demo.util;

import java.util.Comparator;

public class NameComparator implements Comparator<String> {

	public int compare(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return PinYinUtils.populatePinYing(arg0).compareTo(
				PinYinUtils.populatePinYing(arg1));
	}

}
