package com.dat.whm.web.common;

public class Theme {
	private String name;
	private String imageUrl;
	
	private Theme(){}
	
	public Theme(String name, String imageUrl) {
		this.name = name;
		this.imageUrl = imageUrl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
