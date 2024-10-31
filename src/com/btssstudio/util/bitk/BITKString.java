package com.btssstudio.util.bitk;

public class BITKString {
	public String key;
	public String string = "Waiting for Translate!";
	/**
	 * This constructor automatic translates the {@link BITKString} Object constructed.
	 * If you want to translate it yourself, you can use {@link BITKString#BITKString(String, true)}
	 * @param key The key of translation.
	 */
	public BITKString(String key) {
		this(key, false);
	}
	public BITKString(String key, boolean withoutTranslate) {
		this.key = key;
		if(!withoutTranslate) {
			BITK.translate(this);
		}
	}
	public String getKey() {
		return key;
	}
	public void applyTranslate(String string) {
		this.string = string;
	}
	public String get() {
		return string;
	}
	@Override
	public boolean equals(Object s) {
		if(s instanceof BITKString) {
			BITKString bs = (BITKString)s;
			if(this.key.equals(bs.getKey())) {
				return true;
			}
		}
		return false;
	}
}
