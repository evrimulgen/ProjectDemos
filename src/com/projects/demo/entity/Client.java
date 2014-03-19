package com.projects.demo.entity;

public class Client {
	private String strategy;
	private String id;
	private String version;
	private String size;

	public Client(String strategy, String id, String version, String size) {
		super();
		this.strategy = strategy;
		this.id = id;
		this.version = version;
		this.size = size;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
