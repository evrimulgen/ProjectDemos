package com.projects.demo.printer;

import com.wizarpos.devices.AccessException;

public abstract class ConstantPrint {

	static final String TAG_TITLE = "�ν���(½�����)";

	static final String TAG_ORDER_ID = "���ţ�0000269131126133160602";

	static final String TAG_TYPE = "���ͣ�";

	static final String TAG_TYPE_A = "�㵥";

	static final String TAG_TYPE_B = "����";

	static final String TAG_TYPE_C = "����";

	static final String TAG_POSITION = "���ţ�";

	static final String TAG_OPERATOR = "����Ա��";

	static final String TAG_SEPARATOR = "********************************";

	static final String[] TAG_TABLE_ROW_1 = new String[] { "���մ���", "1", "6.00",
			"6.00" };
	static final String[] TAG_TABLE_ROW_2 = new String[] { "��������", "1",
			"10.00", "10.00" };
	static final String[] TAG_TABLE = new String[] { "��Ŀ", "����", "����", "С��" };

	static final String TAG_TOTAL = "�ܼƣ�";

	static final String TAG_PAY = "�Ѹ���";

	/******************* type c *********************/
	static final String TAG_DELIVERY_TIME = "�Ͳ�ʱ�䣺";

	static final String TAG_DELIVERY_POSITION = "�Ͳ͵�ַ��";

	static final String TAG_CONTACT = "��ϵ�ˣ�";

	static final String TAG_TEL = "��ϵ�绰��";

	public abstract void print() throws AccessException;
}
