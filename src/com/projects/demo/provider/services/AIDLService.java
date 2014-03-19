package com.projects.demo.provider.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.projects.demo.provider.aidl.IAIDLService;

public class AIDLService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(this.getClass().getName(), "onBind()");
		return new ImplAIDLService(); //这个返回值需要一个服务对象。  
	}

	@Override
	public void onCreate() {
		Log.d(this.getClass().getName(), "onCreate()");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(this.getClass().getName(), "onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(this.getClass().getName(), "onStart()");
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(this.getClass().getName(), "onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(this.getClass().getName(), "onUnbind()");
		return super.onUnbind(intent);
	}

	public class ImplAIDLService extends IAIDLService.Stub {  
        @Override  
        public int getId() throws RemoteException {  
            return 123;  
        }  
    }
}
