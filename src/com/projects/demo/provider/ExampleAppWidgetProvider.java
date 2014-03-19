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
	//����һ�������ַ������ó�����������Action
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
			//getInstance(Context context)	Get the AppWidgetManager instance to use for the supplied Context object.��̬������
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			
			ComponentName componentName = new ComponentName(context,ExampleAppWidgetProvider.class);
			appWidgetManager.updateAppWidget(componentName, remoteViews);
		} else {
			//����һ��Ҫ��ӣ�eles���֣���Ȼ��onReceive����ȥ���������ķ���������������������������棬
			//�ͻ�ÿ������onUpdate,onDeleted�ȷ������ͻ��������Σ���ΪUPDATE_ACTION.equals(action)���óɹ�������һ�Σ�
			//uper.onReceive(context, intent)���óɹ��ֻ�����һ�Σ�����ϵͳ�Զ���ġ�
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		System.out.println("onUpdated");

		//����һ��Intent����
		Intent intent = new Intent();
		//ΪIntent��������Action
		intent.setAction(UPDATE_ACTION);
		//ʹ��getBroadcast�������õ�һ��PendingIntent���󣬵��ö���ִ��ʱ���ᷢ��һ���㲥
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.example_appwidget);
		
		remoteViews.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
}