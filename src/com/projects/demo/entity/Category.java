package com.projects.demo.entity;

public class Category {
	private String id;
	private String name;
	private String hash;
	private String icon;
	private String priority;

	public Category(String id, String name, String hash, String icon,
			String priority) {
		super();
		this.id = id;
		this.name = name;
		this.hash = hash;
		this.icon = icon;
		this.priority = priority;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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
}