package org.net.perorin.voiceroid.phraseDic;

import java.util.ArrayList;

public class PhraseElement {

	String pose = "";

	private boolean longPose = false;
	private boolean shortPose = false;
	private String pit = "";
	private String emph = "";
	private String spd = "";
	private String vol = "";
	private String poseLength = "";
	private String question = "";

	ArrayList<ElementChar> elementChars = new ArrayList<ElementChar>();

	public void setLongPose() {
		removeShortPose();
		if (!shortPose) {
			pose = "$2_2";
		}
		longPose = true;
	}

	public void removeLongPose() {
		longPose = false;
		pose = "";
	}

	public void setShortPose() {
		removeLongPose();
		if (!longPose) {
			pose = "$1_1";
		}
		shortPose = true;
	}

	public void removeShortPose() {
		shortPose = false;
		pose = "";
	}

	public void setElementChars(ElementChar ec) {
		elementChars.add(ec);
	}

	public String getPit() {
		return pit;
	}

	public String getEmph() {
		return emph;
	}

	public String getSpd() {
		return spd;
	}

	public String getVol() {
		return vol;
	}

	public String getPoseLength() {
		return poseLength;
	}

	public String getQuestion() {
		return question;
	}

	public void setPit(String pit) {
		this.pit = pit;
	}

	public void setEmph(String emph) {
		this.emph = emph;
	}

	public void setSpd(String spd) {
		this.spd = spd;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public void setPoseLength(String poseLength) {
		this.poseLength = poseLength;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		String ret = "";
		if (!"".equals(pit)) {
			ret += "|pit=" + pit;
		}
		if (!"".equals(emph)) {
			ret += "|emph=" + emph;
		}
		if (!"".equals(spd)) {
			ret += "|spd=" + spd;
		}
		if (!"".equals(vol)) {
			ret += "|vol=" + vol;
		}
		if (!"".equals(poseLength)) {
			ret += "|poseLength=" + poseLength;
		}
		if (!"".equals(pose)) {
			ret += "|" + pose;
		}
		for (ElementChar ec : elementChars) {
			ret += "|" + ec.getPhraseChar();
		}
		if (!"".equals(question)) {
			ret += "|" + question;
		}

		ret += "|";

		return ret;
	}

}
