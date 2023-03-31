package com.patryk.gitdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

	@JsonProperty("size")
	// Github repo default size is in Kilobytes
	private int size;

	@JsonProperty("name")
	private String name;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Repository [size=" + size + ", name=" + name + "]";
	}
}