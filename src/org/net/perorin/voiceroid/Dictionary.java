package org.net.perorin.voiceroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.net.perorin.exception.PhraseDicException;

public class Dictionary {

	private String componentName = "";
	private String componentVersion = "";
	private String updateDateTime = "";
	private String type = "";
	private String version = "";
	private String dialect = "";
	private int count = 0;

	public static void main(String[] args) throws IOException, PhraseDicException {
		Dictionary dic = new Dictionary("C:/Users/perorin/Documents/VOICEROID＋ 関西弁/フレーズ辞書/user.pdic");
	}

	public Dictionary(String filepath) throws IOException, PhraseDicException {
		this(new File(filepath));
	}

	public Dictionary(File file) throws IOException, PhraseDicException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
		String line = br.readLine();
		initInformation(line);
		br.close();
	}

	private void initInformation(String line) throws PhraseDicException {
		String buf0[] = line.split("ComponentName=");
		String buf1[] = buf0[1].split("ComponentVersion=");
		String buf2[] = buf1[1].split("UpdateDateTime=");
		String buf3[] = buf2[1].split("Type=");
		String buf4[] = buf3[1].split("Version=");
		String buf5[] = buf4[1].split("Dialect=");
		String buf6[] = buf5[1].split("Count=");

		componentName = buf0[1].split("\"")[1];
		componentVersion = buf1[1].split("\"")[1];
		updateDateTime = buf2[1].split("\"")[1];
		type = buf3[1].split("\"")[1];
		version = buf4[1].split("\"")[1];
		dialect = buf5[1].split("\"")[1];
		try {
			count = Integer.parseInt(buf6[1].split("\"")[1]);
		} catch (NumberFormatException e) {
		}
	}

	public String getComponentName() {
		return componentName;
	}

	public String getComponentVersion() {
		return componentVersion;
	}

	public String getUpdateDateTime() {
		return updateDateTime;
	}

	public String getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	public String getDialect() {
		return dialect;
	}

	public int getCount() {
		return count;
	}

}
