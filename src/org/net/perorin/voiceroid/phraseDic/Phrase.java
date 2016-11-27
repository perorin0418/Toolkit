package org.net.perorin.voiceroid.phraseDic;

import java.util.ArrayList;

public class Phrase {

	private int num = 0;
	private String sentence = "";
	private ArrayList<PhraseElement> elementList = null;

	public Phrase(String num, String sentence, String elements) {
		setNum(num);
		setSentence(sentence);
		setElement(elements);
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

	private void setElement(String elements) {
		elementList = new ArrayList<PhraseElement>();
		char array[] = elements.toCharArray();
		PhraseElement element = new PhraseElement();
		for (int i = 0; i < array.length; i++) {
			switch (array[i]) {

			// 長ポーズ、短ポーズ
			case '$':
				if (element.toString().length() >= 0 && elementList.size() != 0) {
					elementList.add(element);
					element = new PhraseElement();
				}
				i++;

				// 短ポーズ
				if (array[i] == '1') {
					element.setShortPose();
				}
				// 長ポーズ
				else if (array[i] == '2') {
					element.setLongPose();
				} else {
					// ここは通らないはず
				}
				i += 2;
				break;

			// よくわかんないけど、たぶんポーズ無しって意味
			case '0':
				element.setElementChars(new ElementChar(array[i]));
				break;

			// 上イントネーション
			case '^':
				i++;
				if (array[i + 1] == 'D') {
					element.setElementChars(new ElementChar(array[i], '^', 'D'));
				} else if (array[i + 1] == 'V') {
					element.setElementChars(new ElementChar(array[i], '^', 'V'));
				} else {
					element.setElementChars(new ElementChar(array[i], '^'));
				}
				break;

			// 下イントネーション
			case '!':
				i++;
				if (array[i + 1] == 'D') {
					element.setElementChars(new ElementChar(array[i], '!', 'D'));
				} else if (array[i + 1] == 'V') {
					element.setElementChars(new ElementChar(array[i], '!', 'V'));
				} else {
					element.setElementChars(new ElementChar(array[i], '!'));
				}
				break;

			// 高さ、任意ポーズ長、抑揚、話速、音量
			case '(':
				i++;

				// 高さ、任意ポーズ長
				if (array[i] == 'P') {
					i++;

					// 高さ
					if (array[i] == 'i') {
						i += 3;
						if (array[i] == 'R') {
							i += 6;
							element.setPit("REVERT");
						} else {
							i += 9;
							char buf[] = new char[4];
							System.arraycopy(array, i, buf, 0, 4);
							element.setPit(String.valueOf(buf));
							i += 4;
						}
					}

					// 任意ポーズ長
					else if (array[i] == 'a') {
						i += 8;
						String poseLength = "";
						while (true) {
							poseLength += array[i];
							i++;
							if (array[i] == ')') {
								break;
							}
						}
						element.setPoseLength(poseLength);
					}
				}

				// 抑揚
				else if (array[i] == 'E') {
					i += 5;
					if (array[i] == 'R') {
						i += 6;
						element.setEmph("REVERT");
					} else {
						i += 9;
						char buf[] = new char[4];
						System.arraycopy(array, i, buf, 0, 4);
						element.setEmph(String.valueOf(buf));
						i += 4;
					}
				}

				// 話速
				else if (array[i] == 'S') {
					i += 4;
					if (array[i] == 'R') {
						i += 6;
						element.setSpd("REVERT");
					} else {
						i += 9;
						char buf[] = new char[4];
						System.arraycopy(array, i, buf, 0, 4);
						element.setSpd(String.valueOf(buf));
						i += 4;
					}
				}

				// 音量
				else if (array[i] == 'V') {
					i += 4;
					if (array[i] == 'R') {
						i += 6;
						element.setVol("REVERT");
					} else {
						i += 9;
						char buf[] = new char[4];
						System.arraycopy(array, i, buf, 0, 4);
						element.setVol(String.valueOf(buf));
						i += 4;
					}
				}

				break;

			// 疑問調読み上げ有り無し
			case '<':
				i++;
				// 読み上げ無し
				if (array[i] == 'F') {
					element.setQuestion("<F>");
					i += 2;
				}
				// 読み上げあり
				else if (array[i] == 'R') {
					element.setQuestion("<R>");
					i += 2;
				}
				break;

			//
			case ')':
				break;

			//
			case 'D':
				break;

			//
			case 'V':
				break;

			// フレーズエレメントの切れ目
			case '|':
				i++;
				elementList.add(element);
				element = new PhraseElement();

			default:
				if (i + 1 >= array.length) {
					element.setElementChars(new ElementChar(array[i]));
				} else {
					if (array[i + 1] == 'D') {
						element.setElementChars(new ElementChar(array[i], 'D'));
					} else if (array[i + 1] == 'V') {
						element.setElementChars(new ElementChar(array[i], 'V'));
					} else {
						element.setElementChars(new ElementChar(array[i]));
					}
				}
				break;
			}
		}
		elementList.add(element);
		System.out.println("***********************************");
		for (PhraseElement pe : elementList) {
			System.out.println(pe);
		}
	}

}
