package com.projects.demo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.projects.demo.R;

public class WebViewActivity extends Activity {

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
				WebViewActivity.this.setProgress(progress * 100);
			}
		});

		// load html files from assets
		// webView.loadUrl("file:///android_asset/html/index.html");
		// webView.loadUrl(url + ":" + port);
		webView.loadUrl("http://blog.csdn.net");
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

	/**
	 * @author Administrator
	 * deal clicking hyper link
	 */
	class webViewClient extends WebViewClient {
		
		// override shouldOverrideUrlLoading method, avoid to open other browsers when click the hyper link

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);
			// return true if you don't want to deal the click event on link, otherwise return false
			return true;
		}
	}

}
