package com.projects.demo.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.projects.demo.R;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
	//定义一个常量字符串，该常量用于命名Action
	private static final String UPDATE_ACTION = "learn.test.UPDATE_APP_WIDGET";
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		System.out.println("onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		System.out.println("onDisabled");
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		System.out.println("onEnabled");
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("onReceive");

		String action = intent.getAction();
		if (UPDATE_ACTION.equals(action)) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.example_appwidget);
			remoteViews.setTextViewText(R.id.test_text, "this is OnReceive");
			//getInstance(Context context)	Get the AppWidgetManager instance to use for the supplied Context object.静态方法。
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			
			ComponentName componentName = new ComponentName(context,ExampleAppWidgetProvider.class);
			appWidgetManager.updateAppWidget(componentName, remoteViews);
		} else {
			//这里一定要添加，eles部分，不然，onReceive不会去调用其它的方法。但是如果把这条语句放在外面，
			//就会每次运行onUpdate,onDeleted等方法，就会运行两次，因为UPDATE_ACTION.equals(action)配置成功会运行一次，
			//uper.onReceive(context, intent)配置成功又会运行一次，后都是系统自定义的。
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		System.out.println("onUpdated");

		//创建一个Intent对象
		Intent intent = new Intent();
		//为Intent对象设置Action
		intent.setAction(UPDATE_ACTION);
		//使用getBroadcast方法，得到一个PendingIntent对象，当该对象执行时，会发送一个广播
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.example_appwidget);
		
		remoteViews.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
}