package com.btssstudio.util.bitk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

/**
 * This class contains many static methods used by BITK. You should call {@link #setBITKLangDirectoryPath(String)}{@link #setTranslateTarget(IBITKLanguage)}{@link #init(boolean)} before using BITK
 */
public class BITK {
	public static IBITKLanguage activeLang;
	public static String langPath;
	public static Map<String, String> translateMap = new HashMap<String, String>();
	public static Gson gson = new Gson();
	protected static Logger logger = Logger.getLogger("BITK");
	public static boolean initFlag = false;
	/**
	 * @param path must be set to a valid path in disk.
	 * This method tell BITK where are the lang.json files.
	 */
	public static void setBITKLangDirectoryPath(String path) {
		langPath = path;
		if(langPath.substring(langPath.length() - 1).equals("/")) {
			StringBuilder sb = new StringBuilder(langPath);
			sb.deleteCharAt(langPath.length() - 1);
			langPath = sb.toString();
		}
	}
	/**
	 * If you want to use a language file but you don't want to create a new {@link IBITKLanguage}, you can call this.
	 * NOTE: If you called this, don't call {@link #setTranslateTarget(IBITKLanguage)}
	 * @param name The name of your custom lang file WITHOUT ".json" SUFFIX!
	 */
	public static void useCustomLangFile(String name) {
		IBITKLanguage lang = new IBITKLanguage() {
			@Override
			public String getKey() {
				return name;
			}
		};
		activeLang = lang;
	}
	public static IBITKLanguage getTranslateTarget() {
		return activeLang;
	}
	/**
	 * When call {@link #init} and {@link #translate(BITKString)}, they will read and translate to the activated language set by this method.
	 * @param lang is the primary language you want to use. 
	 */
	public static void setTranslateTarget(IBITKLanguage lang) {
		activeLang = lang;
	}
	/**
	 * This method initialize BITK and read the translations from file to memory.
	 * @param withoutDirectoryCheck If set to true, BITK will not check directory and files, but maybe cause {@link java.io.FileNotFoundException}.
	 */
	public static void init() {
		init(false);
	}
	public static void init(boolean withoutDirectoryCheck) {
		translateMap.clear();
		initFlag = false;
		if(!withoutDirectoryCheck) {
			File d = new File(langPath);
			if(!d.exists() && !d.isDirectory())
				d.mkdir();
		}
		File f = new File(langPath + "/" + activeLang.getKey() + ".json");
		if(!f.exists() || f.isDirectory()) {
			try {
				logger.log(Level.WARNING, "Language file doesn't exist! Created an empty file, please fill it with codes!");
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(f));
				BITKString[] ss = gson.fromJson(br, BITKString[].class);
				if(ss == null) return;
				for(BITKString s : ss) {
					translateMap.put(s.getKey(), s.get());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		initFlag = true;
	}
	/**
	 * This method translates a {@link BITKString} to the ACTIVATED LANGUAGE.
	 * The method automatic call on when a {@link BITKString} Object constructed using constructor {@link BITKString#BITKString(String)}.
	 * @param toTranslate The BITKString Object to translate.
	 * @throws BITKInitFailedException If you don't call {@link #init(boolean)}, the exception will be throw.
	 */
	public static void translate(BITKString toTranslate) {
		if(!initFlag)
			throw new IllegalStateException("BITK Init Failed!");
		String key = toTranslate.getKey();
		String string = translateMap.get(key);
		if(string != null)
			toTranslate.applyTranslate(string);
	}
	/**
	 * WARNING:THIS METHOD MAY CAUSE LAG. USE WITH CAUTION!
	 * @param target The language you want to translate to.
	 */
	public static void translate(BITKString toTranslate, IBITKLanguage target) {
		if(!initFlag)
			throw new IllegalStateException("BITK Init Failed!");
		File f = new File(langPath + "/" + target.getKey() + ".json");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			BITKString[] str = gson.fromJson(br, BITKString[].class);
			for(BITKString s : str) {
				if(s.equals(toTranslate)) {
					toTranslate.applyTranslate(s.string);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
