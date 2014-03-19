package com.projects.demo;

import java.io.DataOutputStream;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.projects.demo.util.Log;

public class Welcome extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		new Thread(runnable).start();
		//��ǰӦ�õĴ���ִ��Ŀ¼
        upgradeRootPermission(getPackageCodePath());
        Log.setEnabled(true);
        /*Log.setPath("/mnt/sdcard/projectDemo/android/log", "log", "log");
        Log.setPolicy(Log.LOG_ALL_TO_FILE);*/
        Log.d(Welcome.class.getSimpleName());
        Log.i(Welcome.class.getSimpleName());
        Log.v(Welcome.class.getSimpleName());
        Log.d(Welcome.class.getSimpleName());
        Log.e(Welcome.class.getSimpleName());
        Log.e(Welcome.class.getSimpleName());
        Log.v(Welcome.class.getSimpleName());
        Log.d(Welcome.class.getSimpleName());
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Intent intent = new Intent(Welcome.this, ProjectsActivity.class);
					startActivity(intent);
					finish();
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};
	
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
			}
			handler.sendEmptyMessage(0);
		}
	};
	
	/**
     * Ӧ�ó������������ȡ RootȨ�ޣ��豸�������ƽ�(���ROOTȨ��)
     * 
     * @return Ӧ�ó�����/���ȡRootȨ��
     */
    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd="chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //�л���root�ʺ�
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }
}
