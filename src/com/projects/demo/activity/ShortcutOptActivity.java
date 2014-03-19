package com.projects.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.projects.demo.R;

public class ShortcutOptActivity extends Activity implements View.OnClickListener {
	
	private Button btnAddShortcut;
	private Button btnDelShortcut;
	private String appClass; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shortcut_demo_main);
		
		btnAddShortcut = (Button) findViewById(R.id.btnAddShortcut);
		btnAddShortcut.setOnClickListener(this);
		btnDelShortcut = (Button) findViewById(R.id.btnDelShortcut);
		btnDelShortcut.setOnClickListener(this);
		
		appClass = this.getPackageName() + "." + this.getLocalClassName();
	}
	
	/** 
	* �������������ݷ�ʽ 
	*/
	private void addShortcut() { 
	    Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); 
	      
	    //��ݷ�ʽ������ 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)); 
	    shortcut.putExtra("duplicate", false); //�������ظ����� 
	      
	    //ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer 
	    //ע��: ComponentName�ĵڶ�������������ϵ��(.)�������ݷ�ʽ�޷�������Ӧ���� 
	    /*ComponentName comp = new ComponentName(this.getPackageName(), appClass); 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));*/ 
	    Intent intent = new Intent(this, ShortcutOptActivity.class);
	    // ��������������Ϊ�˵�Ӧ�ó���ж��ʱ�����ϵĿ�ݷ�ʽ��ɾ��  
	    intent.setAction("android.intent.action.MAIN");  
	    intent.addCategory("android.intent.category.LAUNCHER");
	    intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent); 
	      
	    //��ݷ�ʽ��ͼ�� 
	    ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher); 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes); 
	      
	    sendBroadcast(shortcut); 
	} 
	  
	/** 
	* ɾ������Ŀ�ݷ�ʽ 
	*/
	private void delShortcut() { 
	    Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT"); 
	      
	    //��ݷ�ʽ������ 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)); 
	      
	    //ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer 
	    //ע��: ComponentName�ĵڶ�����������������������������+�������������޷�ɾ����ݷ�ʽ 
	   /* ComponentName comp = new ComponentName(this.getPackageName(), appClass); 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp)); */
	    Intent intent = new Intent(this, ShortcutOptActivity.class);
	    // ��������������Ϊ�˵�Ӧ�ó���ж��ʱ�����ϵĿ�ݷ�ʽ��ɾ��  
	    intent.setAction("android.intent.action.MAIN");  
	    intent.addCategory("android.intent.category.LAUNCHER");
	    intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent); 
	      
	    sendBroadcast(shortcut); 
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnAddShortcut:
			addShortcut();
			break;
		case R.id.btnDelShortcut:
			delShortcut();
			break;

		default:
			break;
		}
	} 


}
