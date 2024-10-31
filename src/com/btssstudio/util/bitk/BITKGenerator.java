package com.btssstudio.util.bitk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class BITKGenerator {
	private static OutputStreamWriter osw = null;
	private static List<BITKString> bstr = new ArrayList<BITKString>();
	/**
	 * Initializes the {@link BITKGenerator}. Creates the lang file.
	 * @param path The path you want to store your lang file.
	 */
	public static void init(String path) {
		File f = new File(path);
		if(!f.exists() || f.isDirectory()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			osw = new OutputStreamWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e) {
			BITK.logger.log(Level.SEVERE, "Check if \"" + path + "\" is not correct.");
			e.printStackTrace();
		}
	}
	/**
	 * {@link BITKGenerator} doesn't generates Json instantly.
	 * This method adds an Object to a List and wait for generate.
	 */
	public static void addToList(String key, String str) {
		BITKString s = new BITKString(key, true);
		s.applyTranslate(str);
		bstr.add(s);
	}
	public static void addToList(BITKString str) {
		bstr.add(str);
	}
	/**
	 * Turns everything to Json, save them, and quit the Generator.  
	 */
	public static void saveAndQuit() {
		try {
			osw.write(BITK.gson.toJson(bstr));
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
