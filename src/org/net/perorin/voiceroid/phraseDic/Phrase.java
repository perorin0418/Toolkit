package org.net.perorin.voiceroid.phraseDic;

import java.util.ArrayList;

public class Phrase {

	private int num = 0;
	private String sentence = "";
	private ArrayList<PhraseElement> elementList = null;

	public Phrase(String num,String sentence,String elements) {
		setNum(num);
		setSentence(sentence);
	}

	public int getNum() {
		return num;
	}

	private void setNum(String num) {
		String buf[] = num.split(":");
		try {
			this.num = Integer.parseInt(buf[1]);
		} catch (NumberFormatException e) {
			this.num = -1;
		}
	}

	public String getSentence() {
		return sentence;
	}

	private void setSentence(String sentence) {
		this.sentence = sentence;
	}

}
