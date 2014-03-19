package com.projects.demo.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.util.Env;

public class AndroidFileListenerActivity extends Activity {
	private FileObserver mFileObserver;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filelistener);

		if (null == mFileObserver) {
			mFileObserver = new SDCardFileObserver(Env.getExternalStorageDirectory().getAbsolutePath());
			mFileObserver.startWatching(); // ��ʼ����
		}

		Button deleteButton = (Button) findViewById(R.id.deleteFile);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteDir(Env.getImageCacheDirectory(AndroidFileListenerActivity.this));
			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(AndroidFileListenerActivity.this, "delete file",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	public void onDestory() {
		if (null != mFileObserver)
			System.out.println("stopWatching");
			mFileObserver.stopWatching(); // ֹͣ����
	}

	private class SDCardFileObserver extends FileObserver {
		// mask:ָ��Ҫ�������¼����ͣ�Ĭ��ΪFileObserver.ALL_EVENTS
		public SDCardFileObserver(String path, int mask) {
			super(path, mask);
		}

		public SDCardFileObserver(String path) {
			super(path);
		}

		@Override
		public void onEvent(int event, String path) {
			final int action = event & FileObserver.ALL_EVENTS;
			switch (action) {
			case FileObserver.ACCESS:
				System.out.println("event: �ļ���Ŀ¼������, path: " + path);
				break;

			case FileObserver.DELETE:
				System.out.println("event: �ļ���Ŀ¼��ɾ��, path: " + path);
				handler.sendEmptyMessage(0);
				break;

			case FileObserver.OPEN:
				System.out.println("event: �ļ���Ŀ¼����, path: " + path);
				break;

			case FileObserver.MODIFY:
				System.out.println("event: �ļ���Ŀ¼���޸�, path: " + path);
				break;
			}
		}

	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0,size = children.length; i< size; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so now it can be smoked
		return dir.delete();
	}
}
