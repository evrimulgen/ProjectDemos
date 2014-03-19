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
	* 创建程序桌面快捷方式 
	*/
	private void addShortcut() { 
	    Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); 
	      
	    //快捷方式的名称 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)); 
	    shortcut.putExtra("duplicate", false); //不允许重复创建 
	      
	    //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer 
	    //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序 
	    /*ComponentName comp = new ComponentName(this.getPackageName(), appClass); 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));*/ 
	    Intent intent = new Intent(this, ShortcutOptActivity.class);
	    // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除  
	    intent.setAction("android.intent.action.MAIN");  
	    intent.addCategory("android.intent.category.LAUNCHER");
	    intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent); 
	      
	    //快捷方式的图标 
	    ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher); 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes); 
	      
	    sendBroadcast(shortcut); 
	} 
	  
	/** 
	* 删除程序的快捷方式 
	*/
	private void delShortcut() { 
	    Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT"); 
	      
	    //快捷方式的名称 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)); 
	      
	    //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer 
	    //注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式 
	   /* ComponentName comp = new ComponentName(this.getPackageName(), appClass); 
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp)); */
	    Intent intent = new Intent(this, ShortcutOptActivity.class);
	    // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除  
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
