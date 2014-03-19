package com.projects.demo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.printer.ConstantPrint;
import com.projects.demo.printer.HallFood;
import com.projects.demo.printer.OutsideSells;
import com.wizarpos.devices.AccessException;

public class WebViewJSActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_main);

		findViews();
		readHtmlFromAssets();
	}

	/**
	 * get views
	 */
	private void findViews() {
		// get webview components
		webView = (WebView) findViewById(R.id.webview);
		// allow javascript
		webView.getSettings().setJavaScriptEnabled(true);
	}

	private void readHtmlFromAssets() {
		WebSettings webSettings = webView.getSettings();

		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);

		webView.setBackgroundColor(Color.TRANSPARENT); // set WebView backgroud
														// color TRANSPARENT
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activity and webview decide progression, the progress
				// disappear when 100% loaded
				WebViewJSActivity.this.setProgress(progress * 100);
			}
		});
		
		/*
         * DemoJavaScriptInterface类为js调用android服务器端提供接口
         * android 作为DemoJavaScriptInterface类的客户端接口被js调用
         * 调用的具体方法在DemoJavaScriptInterface中定义
         * 例如该实例中的clickOnAndroid
         */
        webView.addJavascriptInterface(new JavaScriptInterface(), "android");
        webView.loadUrl("file:///android_asset/html/js_java_index.html");

		// load html files from assets
		// webView.loadUrl("file:///android_asset/html/index.html");
		// webView.loadUrl(url + ":" + port);
		
//		webView.loadUrl("http://blog.csdn.net");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	Handler mHandler = new Handler();

	/**
	 * @author Administrator
	 * deal clicking hyper link
	 */
	class MyWebViewClient extends WebViewClient {
		
		// override shouldOverrideUrlLoading method, avoid to open other browsers when click the hyper link

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);
			// return true if you don't want to deal the click event on link, otherwise return false
			return true;
		}
	}
	
	final class JavaScriptInterface {
        JavaScriptInterface() {}
  
        /** 
         * 该方法被浏览器端调用 
         */  
        public void clickOnAndroid() {  
            mHandler.post(new Runnable() {  
                public void run() {  
                    //调用js中的onJsAndroid方法  
                	Toast.makeText(getApplicationContext(), "invoked by js", Toast.LENGTH_LONG).show();
                	ConstantPrint c = new HallFood();
    				ConstantPrint c2 = new OutsideSells();
    				try {
    					c.print();
    					c2.print();
    				} catch (AccessException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                	webView.loadUrl("javascript:onJsAndroid()");  
                }  
            });  
        }  
    } 

}
