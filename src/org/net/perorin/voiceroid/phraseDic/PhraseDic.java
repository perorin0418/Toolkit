package org.net.perorin.voiceroid.phraseDic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.net.perorin.exception.PhraseDicException;
import org.net.perorin.voiceroid.Dictionary;

public class PhraseDic extends Dictionary {

	private ArrayList<Phrase> phraseList = null;

	public static void main(String[] args) throws IOException, PhraseDicException {
		PhraseDic dic = new PhraseDic("C:/Users/perorin/Documents/VOICEROID＋ 関西弁/フレーズ辞書/user.pdic");
	}

	public PhraseDic(String filepath) throws IOException, PhraseDicException {
		this(new File(filepath));
	}

	public PhraseDic(File file) throws IOException, PhraseDicException {
		super(file);
		phraseList = new ArrayList<Phrase>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Shift-JIS"));
		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			String num = line;
			String sentence = br.readLine();
			String elements = br.readLine();
			phraseList.add(new Phrase(num, sentence, elements));
		}
		br.close();
	}
}
