package com.projects.demo.entity;

public class App {
	private String id;
	private String name;
	private String version;
	private String size;
	private String icon;
	private String downloadId;

	public App(String id, String name, String version, String size,
			String icon, String downloadId) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
		this.size = size;
		this.icon = icon;
		this.setDownloadId(downloadId);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(String downloadId) {
		this.downloadId = downloadId;
	}

	public String getIconFileName() {
		return this.getIcon() + "_" + this.getId() + ".png";
	}
}