package com.projects.demo.printer;

import com.wizarpos.devices.AccessException;

public abstract class ConstantPrint {

	static final String TAG_TITLE = "俏江南(陆家嘴店)";

	static final String TAG_ORDER_ID = "单号：0000269131126133160602";

	static final String TAG_TYPE = "类型：";

	static final String TAG_TYPE_A = "点单";

	static final String TAG_TYPE_B = "订座";

	static final String TAG_TYPE_C = "外卖";

	static final String TAG_POSITION = "桌号：";

	static final String TAG_OPERATOR = "操作员：";

	static final String TAG_SEPARATOR = "********************************";

	static final String[] TAG_TABLE_ROW_1 = new String[] { "红烧大排", "1", "6.00",
			"6.00" };
	static final String[] TAG_TABLE_ROW_2 = new String[] { "菜肉大馄饨", "1",
			"10.00", "10.00" };
	static final String[] TAG_TABLE = new String[] { "项目", "数量", "单价", "小计" };

	static final String TAG_TOTAL = "总计：";

	static final String TAG_PAY = "已付：";

	/******************* type c *********************/
	static final String TAG_DELIVERY_TIME = "送餐时间：";

	static final String TAG_DELIVERY_POSITION = "送餐地址：";

	static final String TAG_CONTACT = "联系人：";

	static final String TAG_TEL = "联系电话：";

	public abstract void print() throws AccessException;
}
