package com.btssstudio.util.bitk;

public enum Language implements IBITKLanguage{
	ZH_CN("zh_cn"),
	EN_US("en_us");
	
	public String key;
	Language(String key) {
		this.key = key;
	}
	public String getKey() {
		return this.key;
	}
}
