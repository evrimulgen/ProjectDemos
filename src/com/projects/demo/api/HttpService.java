package com.projects.demo.api;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpService {
	
	public static int CONNECTION_TIMEOUT = 30 * 1000;// 1;
	public static int SO_TIMEOUT = 30 * 1000;
	private static final String USER_AGENT = "AllInPay-Network";
	public static final HttpParams HTTP_PARAMS;
	static {
		// Prepare HTTP parameters.
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, true);
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, CONNECTION_TIMEOUT);
		HttpClientParams.setRedirecting(params, true);
		HttpProtocolParams.setUserAgent(params, USER_AGENT);
		HttpProtocolParams.setUseExpectContinue(params, false);
		HTTP_PARAMS = params;
	}

	public HttpService() {

	}

	public JSONObject sendRequest(String urlString, JSONObject request)
			throws IOException, JSONException {
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		String str = null;
		
		//add ssl param start
		SchemeRegistry schemeRegistry = new SchemeRegistry();  
		schemeRegistry.register(new Scheme("https",  
		                    new EasySSLSocketFactory(), 443));  
		/*schemeRegistry.register(new Scheme("https",  
		                    new EasySSLSocketFactory(), 8443));  */
		ClientConnectionManager connManager = new ThreadSafeClientConnManager(HTTP_PARAMS, schemeRegistry);  
		HttpClient httpClient = new DefaultHttpClient(connManager, HTTP_PARAMS);  
		//add ssl param end

//		HttpClient client = new DefaultHttpClient();
		HttpPost req = new HttpPost(urlString);
		req.setHeader("Content-Type", "application/json;charset=UTF8");
		req.setEntity(new StringEntity(request.toString()));
		System.out.println(request.toString());
		HttpResponse response = httpClient.execute(req);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			in = new BufferedInputStream(response.getEntity().getContent());
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			str = new String(baos.toByteArray(), "utf8");
		} else {
			System.out.println("error: "
					+ response.getStatusLine().getStatusCode());
		}

		return new JSONObject(str);
	}

	/*public String downloadFile(String urlString, JSONObject request,
			String fileName) throws IOException, JSONException {
		InputStream in = null;
		FileService fileService = null;

		HttpClient client = new DefaultHttpClient();
		HttpPost req = new HttpPost(urlString);
		try {
			req.setHeader("Content-Type", "application/json;charset=UTF8");
			req.setEntity(new StringEntity(request.toString()));
			HttpResponse response = client.execute(req);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				in = new BufferedInputStream(response.getEntity().getContent());
				fileService = new FileService();
				return fileService.saveFile(in, fileName);
			} else {
				System.out.println("error: "
						+ response.getStatusLine().getStatusCode());
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return null;
	}*/
}
