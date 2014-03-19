package com.projects.demo.printer;

import com.wizarpos.devices.AccessException;
import com.wizarpos.devices.DeviceManager;
import com.wizarpos.devices.printer.PrinterControl;
import com.wizarpos.impl.devices.printer.entity.CharacterSetting_Command;
import com.wizarpos.impl.devices.printer.entity.FormatSetting_Command;
import com.wizarpos.impl.devices.printer.entity.Printer_Command;

public class OutsideSells extends ConstantPrint {

	public void print() throws AccessException {
		PrinterControl control = DeviceManager.getInstance()
				.getPrinterControl();

		control.open();

		//style: bold, double height, double width, center
		control.sendESC(FormatSetting_Command.getESCan((byte) 0x49)); //center
		control.sendESC(CharacterSetting_Command.getESCEn((byte) 0x01)); //bold
		control.sendESC(CharacterSetting_Command.getGSExclamationN((byte) 0x11)); //double height
		control.printText(TAG_TITLE + "\n\n");
		//cancel style
		control.sendESC(CharacterSetting_Command.getESCEn((byte) 0x00)); //bold
		control.sendESC(CharacterSetting_Command.getGSExclamationN((byte) 0x00)); //double height

		//align center
		String time = "2013-11-29 11:41:23";
		control.printText(time + "\n\n");

		//align left
		control.sendESC(FormatSetting_Command.getESCan((byte) 0x48));
		control.printText(TAG_ORDER_ID + "\n");

		control.printText(TAG_TYPE + TAG_TYPE_C + "\n");

		control.printText(TAG_DELIVERY_TIME + "\n");

		control.printText(TAG_DELIVERY_POSITION + "\n");

		control.printText(TAG_CONTACT + "\n");

		control.printText(TAG_TEL + "\n");

		control.printText(TAG_OPERATOR + "\n");

		control.printText(TAG_SEPARATOR + "\n");

		byte[] cmds = new byte[] { 0x1B, (byte) 0x44, 0x0C, ((byte) (0x12)),
				((byte) (0x1A/* 09*3 */)), 0x00 };//tab
		control.sendESC(cmds);
		for (String s : TAG_TABLE) {
			control.printText(s);
			control.sendESC(Printer_Command.getHt());
		}
		control.printText("\n");
		for (String s : TAG_TABLE_ROW_1) {
			control.printText(s);
			// control.sendESC(cmds);
			control.sendESC(Printer_Command.getHt());
		}
		control.printText("\n");
		for (String s : TAG_TABLE_ROW_2) {
			control.printText(s);
			control.sendESC(Printer_Command.getHt());
		}
		control.printText("\n\n");
		control.printText(TAG_SEPARATOR + "\n");

		control.printText("\t\t" + TAG_TOTAL + "\n");
		control.printText("\t\t" + TAG_PAY + "\n");

		control.close();
	}
}
