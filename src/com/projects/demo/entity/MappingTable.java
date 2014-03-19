package com.projects.demo.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;

import com.projects.demo.api.ApiService;

public class MappingTable {

	private volatile static MappingTable instance = null;

	private List<Category> categories = null;
	private Map<String, Category> cagetory_map = null;
	private Map<String, List<App>> app_map = null;
	private String terminalId;
	private Client updateVersion;

	public Client getUpdateVersion() {
		return updateVersion;
	}

	private MappingTable() {
		categories = new ArrayList<Category>();
		cagetory_map = new HashMap<String, Category>();
		app_map = new HashMap<String, List<App>>();
	}

	private static Object object = new Object();

	public synchronized static MappingTable getInstance() {

		if (instance == null) {
			synchronized (object) {
				if (instance == null) {
					instance = new MappingTable();
					instance.init();
				}
			}
		}
		return instance;
	}

	private void addCategory(Category item) {
		categories.add(item);
		cagetory_map.put(item.getId(), item);
	}

	private void addApp(String categoryId, App app) {
		if (!app_map.containsKey(categoryId)) {
			app_map.put(categoryId, new ArrayList<App>());
		}
		app_map.get(categoryId).add(app);
	}

	public void init() {
		ApiService apiService = ApiService.getInstance();
		String terminalId = null;
		try {
			terminalId = apiService.register();
			List<Category> categoriesTmp = null;
			System.out.println("getCategories");
			categoriesTmp = apiService.getCategories(terminalId);
			System.out.println("categoriesTmp: " + categoriesTmp);
			List<String> categoryIds = new ArrayList<String>();
			for (int i = 0; i < categoriesTmp.size(); i++) {
				Category category = categoriesTmp.get(i);
				categoryIds.add(category.getId());
				addCategory(category);
				System.out.println("addCategory: " + category.getName());
			}
			HashMap<Long, List<App>> appMapping = null;
			System.out.println("getApps");
			appMapping = apiService.getApps(terminalId, categoryIds);
			System.out.println("appMapping: " + appMapping);
			for (Entry<Long, List<App>> entry : appMapping.entrySet()) {
				List<App> apps = entry.getValue();
				for (App app : apps) {
					addApp(entry.getKey().toString(), app);
					System.out.println("addApp: " + app.getName());
				}
			}

			System.out.println("getUpdateVersion");
			updateVersion = apiService.getUpdateVersion(terminalId);
			System.out.println("updateVersion: " + updateVersion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// addCategory(new Category("1", "餐饮美食"));
		// addApp("1", new App("1", "订餐易",
		// "http://192.168.101.34:8080/asweb/download/363"));
		// addApp("1", new App("2", "订餐宝",
		// "http://192.168.101.34:8080/asweb/download/363"));
		// addCategory(new Category("2", "数码家电"));
		// addApp("2", new App("3", "家电测试",
		// "http://192.168.101.34:8080/asweb/download/363"));
		// addCategory(new Category("3", "运动健身"));
		// addApp("3", new App("4", "健身测试",
		// "http://192.168.101.34:8080/asweb/download/363"));
		// addCategory(new Category("4", "美容美发"));
		// addApp("4", new App("5", "博卡",
		// "http://192.168.101.34:8080/asweb/download/363"));
		// addCategory(new Category("5", "百货零售"));
		// addCategory(new Category("6", "教育培训"));

	}

	public List<Category> getCategories() {
		return categories;
	}

	public Map<String, Category> getCagetory_map() {
		return cagetory_map;
	}

	public Map<String, List<App>> getApp_map() {
		return app_map;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void reset() {
		instance = new MappingTable();
		instance.init();
	}

	public void resetClient() {
		ApiService apiService = ApiService.getInstance();
		try {
			System.out.println("getUpdateVersion");
			updateVersion = apiService.getUpdateVersion(terminalId);
			System.out.println("updateVersion: " + updateVersion);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
