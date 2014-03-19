package com.projects.demo.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.projects.demo.entity.App;
import com.projects.demo.entity.Category;
import com.projects.demo.entity.Client;

public class ApiService {

	private static ApiService apiService = null;
//	private static final String HOST = "http://116.236.252.102:30068";
//	private static final String HOST = "http://192.168.1.100:8080";
	private static final String HOST = "https://192.168.101.34";//UAT

	private static final String VERSION_KEY = "version";
	private static final String ACTION_KEY = "action";
	private static final String TERMINALID_KEY = "terminalId";
	private static final String DATA_KEY = "data";
	
	private static final String VERSION_VAL = "1.0";
	private static final String REGISTER_TERMINAL_ACTION = "register";
	private static final String GET_CATEGORIES_ACTION = "getCategories";
	private static final String GET_APPS_ACTION = "getApps";
	private static final String DOWNLOAD_APP_ACTION = "downloadApp";
	private static final String DOWNLOAD_PIC_ACTION = "downloadIcon";
	private static final String SELF_UPDATE_ACTION = "selfUpdate";
	
	private static final String REQUEST_CONTEXT = "/asapi";
	private static final String REGISTER_TERMINAL_PATH = "/appstore/terminal/register";
	private static final String GET_CATEGORIES_PATH = "/appstore/app/getCategories";
	private static final String GET_APPS_PATH = "/appstore/app/getApps";
	private static final String SELF_UPDATE_PATH = "/appstore/client/selfUpdate";
	private static final String DOWNLOAD_PATH = "/appstore/download/download";
	
	private static final String CLIENTVERSION = "1.0.0";
	
	public static ApiService getInstance() {
		if (apiService == null) {
			apiService = new ApiService();
		}
		return apiService;
	}

	private ApiService() {

	}


	public List<Category> getCategories(String terminalId)
			throws JSONException, IOException {
		JSONObject req = generateReq(GET_CATEGORIES_ACTION);
		JSONObject rep = null;

		req.put(TERMINALID_KEY, terminalId);
		req.getJSONObject("data").put("hash", "0");
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + GET_CATEGORIES_PATH;
		System.out.println("getCategories: " + url);
		rep = httpService.sendRequest(url, req);

		List<Category> categories = new ArrayList<Category>();
		JSONArray categoriesJson = rep.getJSONObject(DATA_KEY).getJSONArray(
				"categories");
		int len = categoriesJson.length();
		for (int i = 0; i < len; i++) {
			JSONObject categoryJson = categoriesJson.getJSONObject(i);
			Category category = new Category(categoryJson.getString("id"),
					categoryJson.getString("name"),
					categoryJson.getString("hash"),
					categoryJson.getString("icon"),
					categoryJson.getString("priority"));
			categories.add(category);
		}

		return categories;
	}
	
	public JSONObject getCategoriesJson(String terminalId)
			throws JSONException, IOException {
		JSONObject req = generateReq(GET_CATEGORIES_ACTION);
		JSONObject rep = null;
		
		req.put(TERMINALID_KEY, terminalId);
		req.getJSONObject("data").put("hash", "0");
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + GET_CATEGORIES_PATH;
		System.out.println("getCategories: " + url);
		rep = httpService.sendRequest(url, req);
		
		/*List<Category> categories = new ArrayList<Category>();
		JSONArray categoriesJson = rep.getJSONObject(DATA_KEY).getJSONArray(
				"categories");
		int len = categoriesJson.length();
		for (int i = 0; i < len; i++) {
			JSONObject categoryJson = categoriesJson.getJSONObject(i);
			Category category = new Category(categoryJson.getString("id"),
					categoryJson.getString("name"),
					categoryJson.getString("hash"),
					categoryJson.getString("icon"),
					categoryJson.getString("priority"));
			categories.add(category);
		}*/
		
		return rep;
	}

	public HashMap<Long, List<App>> getApps(String terminalId,
			List<String> categoryIds) throws JSONException, IOException {
		JSONObject req = generateReq(GET_APPS_ACTION);
		JSONObject rep = null;

		req.put(TERMINALID_KEY, terminalId);
		JSONArray categories = new JSONArray();
		for (String id : categoryIds) {
			categories.put(id);
		}
		req.getJSONObject("data").put("categories", categories);
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + GET_APPS_PATH;
		System.out.println("getApps: " + url);
		rep = httpService.sendRequest(url, req);

		HashMap<Long, List<App>> mapping = new HashMap<Long, List<App>>();
		JSONArray categoriesJson = rep.getJSONObject(DATA_KEY).getJSONArray(
				"categories");
		int len = categoriesJson.length();
		System.out.println("len:" + len);
		Long categoryId = null;
		for (int i = 0; i < len; i++) {
			List<App> apps = new ArrayList<App>();
			JSONObject categoryJson = categoriesJson.getJSONObject(i);
			categoryId = categoryJson.getLong("id");
			JSONArray appsJson = categoryJson.getJSONArray("apps");
			int applen = appsJson.length();
			System.out.println("applen:" + applen);
			for (int j = 0; j < applen; j++) {
				JSONObject appJson = appsJson.getJSONObject(j);
				App app = new App(appJson.getString("id"),
						appJson.getString("name"),
						appJson.getString("version"),
						appJson.getString("size"), appJson.getString("icon"),
						appJson.getString("downloadId"));
				apps.add(app);
			}
			mapping.put(categoryId, apps);

		}

		return mapping;
	}

	public Client getUpdateVersion(String terminalId) throws JSONException,
			IOException {
		JSONObject req = generateReq(SELF_UPDATE_ACTION);
		JSONObject rep = null;

		req.put(TERMINALID_KEY, terminalId);
		req.getJSONObject("data").put("version", CLIENTVERSION);
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + SELF_UPDATE_PATH;
		System.out.println("getUpdateVersion: " + url);
		rep = httpService.sendRequest(url, req);

		JSONObject dataJson = rep.getJSONObject(DATA_KEY);
		String strategy = dataJson.getString("strategy");
		if ("0".equals(strategy)) {
			return null;
		}
		JSONObject updateJson = dataJson.getJSONObject("client");

		Client updateVersion = new Client(strategy, updateJson.getString("id"),
				updateJson.getString("version"), updateJson.getString("size"));

		return updateVersion;

	}

	public String register() throws JSONException, IOException {
		JSONObject req = generateReq(REGISTER_TERMINAL_ACTION);
		JSONObject rep = null;

		JSONObject dataJson = req.getJSONObject("data");
		dataJson.put("sn", android.os.Build.SERIAL);
		JSONObject modelJson = new JSONObject();
		dataJson.put("sn", android.os.Build.SERIAL);
		dataJson.put("terminalModel", modelJson);
		modelJson.put("name", android.os.Build.MODEL);
		modelJson.put("cpuInfo", android.os.Build.CPU_ABI);
		modelJson.put("memInfo", "");
		modelJson.put("printerInfo", "");
		modelJson.put("resolutionInfo", "");
		modelJson.put("cardReaderInfo", "");
		dataJson.put("manufacturer", android.os.Build.MANUFACTURER);
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + REGISTER_TERMINAL_PATH;
		System.out.println("register: " + url);
		rep = httpService.sendRequest(url, req);
		System.out.println("rep: " + rep.toString());
		return rep.getJSONObject("data").getString("terminalId");
	}

	/*public String downloadApp(String terminalId, String id, String fileName)
			throws IOException, JSONException {
		JSONObject req = generateReq(DOWNLOAD_APP_ACTION);
		String rep = null;

		req.put(TERMINALID_KEY, terminalId);
		req.getJSONObject("data").put("id", id);
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + DOWNLOAD_PATH;
		System.out.println("downloadApp: " + url);
		rep = httpService.downloadFile(url, req, fileName);

		return rep;
	}

	public String downloadPic(String terminalId, String id, String fileName)
			throws IOException, JSONException {
		JSONObject req = generateReq(DOWNLOAD_PIC_ACTION);
		String rep = null;

		req.put(TERMINALID_KEY, terminalId);
		req.getJSONObject("data").put("id", id);
		HttpService httpService = new HttpService();
		String url = HOST + REQUEST_CONTEXT + DOWNLOAD_PATH;
		System.out.println("downloadPic: " + url);
		rep = httpService.downloadFile(url, req, fileName);

		return rep;
	}*/

	private JSONObject generateReq(String action) throws JSONException {
		JSONObject req = new JSONObject();
		req.put(VERSION_KEY, VERSION_VAL);
		req.put(ACTION_KEY, action);
		JSONObject data = new JSONObject();
		req.put(DATA_KEY, data);
		return req;
	}
}
