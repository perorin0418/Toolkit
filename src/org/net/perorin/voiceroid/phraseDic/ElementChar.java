package org.net.perorin.voiceroid.phraseDic;

public class ElementChar {

	String phraseChar = "";
	String c = "";
	String option = "";
	String voice = "";

	public ElementChar(char c) {
		char buf[] = { c };
		phraseChar = new String(buf);
	}

	public ElementChar(char c, char c2) {
		if (c2 == '!' || c2 == '^') {
			char buf_c2[] = { c2 };
			option = new String(buf_c2);
			char buf_c[] = { c2, c };
			phraseChar = new String(buf_c);
		} else if (c2 == 'D' || c2 == 'V') {
			char buf_c2[] = { c2 };
			voice = new String(buf_c2);
			char buf[] = { c, c2 };
			phraseChar = new String(buf);
		}
	}

	public ElementChar(char c, char option, char voice) {
		char buf_option[] = { option };
		this.option = new String(buf_option);
		char buf_voice[] = { voice };
		this.voice = new String(buf_voice);
		char buf[] = { option, c, voice };
		phraseChar = new String(buf);
	}

	public String getPhraseChar() {
		return phraseChar;
	}

}
