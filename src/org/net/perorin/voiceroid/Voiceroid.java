package org.net.perorin.voiceroid;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.swt.internal.win32.OS;
import org.net.perorin.exception.FailedVoiceSaveException;
import org.net.perorin.exception.HwndNotFoundException;
import org.net.perorin.toolkit.RobotOperator;
import org.net.perorin.win32API.Position;
import org.net.perorin.win32API.Size;
import org.net.perorin.win32API.W32window;

public class Voiceroid {

	public static final String YUKARI_NAME = "結月ゆかり";
	public static final String MAKI_NAME = "弦巻マキ";
	public static final String ZUNKO_NAME = "東北ずん子";
	public static final String AKANE_NAME = "琴葉茜";
	public static final String AOI_NAME = "琴葉葵";
	public static final String YOSHIDA_NAME = "吉田くん";
	public static final String SEIKA_NAME = "京町セイカ";
	public static final String KIRITAN_NAME = "東北きりたん";

	public final Point VOICE_EFFECT = new Point(210, 220);
	public final Point VOLUME = new Point(56, 272);
	public final Point SPEED = new Point(58, 324);
	public final Point TONE = new Point(56, 376);
	public final Point TONE_SCALE = new Point(54, 425);

	public final static String YUKARI_WINDOW_NAME = "VOICEROID＋ 結月ゆかり EX";
	public final static String MAKI_WINDOW_NAME = "VOICEROID＋ 民安ともえ EX";
	public final static String ZUNKO_WINDOW_NAME = "VOICEROID＋ 東北ずん子 EX";
	public final static String AKANE_WINDOW_NAME = "VOICEROID＋ 琴葉茜";
	public final static String AOI_WINDOW_NAME = "VOICEROID＋ 琴葉葵";
	public final static String YOSHIDA_WINDOW_NAME = "VOICEROID＋ 鷹の爪 吉田くん EX";
	public final static String SEIKA_WINDOW_NAME = "VOICEROID＋ 京町セイカ EX";
	public final static String KIRITAN_WINDOW_NAME = "VOICEROID＋ 東北きりたん EX";

	private String voiceSavePath = "./bin";
	private String openSaveWindowPath = "./bin";
	private int castID;
	private W32window win;

	public Voiceroid(int castID) throws HwndNotFoundException {
		this.castID = castID;
		try {
			this.win = new W32window(getCast(castID));
		} catch (HwndNotFoundException e) {
			this.win = new W32window(getCast(castID) + "*");
		}
	}

	public void setParameters(int volume, int speed, int tone, int toneScale) {
		win.showWindow(OS.SW_RESTORE);

		PointerInfo pi = MouseInfo.getPointerInfo();
		Point mp = pi.getLocation();

		OS.SetForegroundWindow(win.getTitleHWND());
		win.setWindowSize(new Size(442, 538));
		Position wp = win.getWindowPos();

		RobotOperator.wait(10);

		MouseLock.run();

		MouseLock.setPos(new Point(VOICE_EFFECT.x + wp.getX(), VOICE_EFFECT.y + wp.getY()));
		RobotOperator.clickLeft();

		MouseLock.setPos(new Point(VOLUME.x + wp.getX(), VOLUME.y + wp.getY()));
		RobotOperator.doubleClickLeft();

		RobotOperator.typeNum((double) volume / 100);

		MouseLock.setPos(new Point(SPEED.x + wp.getX(), SPEED.y + wp.getY()));
		RobotOperator.doubleClickLeft();

		RobotOperator.typeNum((double) speed / 100);

		MouseLock.setPos(new Point(TONE.x + wp.getX(), TONE.y + wp.getY()));
		RobotOperator.doubleClickLeft();

		RobotOperator.typeNum((double) tone / 100);

		MouseLock.setPos(new Point(TONE_SCALE.x + wp.getX(), TONE_SCALE.y + wp.getY()));
		RobotOperator.doubleClickLeft();

		RobotOperator.typeNum((double) toneScale / 100);

		RobotOperator.enter();

		MouseLock.stop();

		OS.SetCursorPos(mp.x, mp.y);
	}

	public void setVoiceText(String voiceText) {
		int hWnd = this.win.getClassHWND("WindowsForms10.RichEdit20W.app.0.378734a", null);
		for (int i = 0; i < voiceText.length(); i++)
			OS.SendMessage(hWnd, OS.WM_CHAR, voiceText.charAt(i), 0);
	}

	public void clearVoiceText(String voiceText) {
		int hWnd = this.win.getClassHWND("WindowsForms10.RichEdit20W.app.0.378734a", null);
		for (int i = 0; i < voiceText.length(); i++)
			OS.SendMessage(hWnd, OS.WM_KEYDOWN, OS.VK_BACK, 0);
	}

	public File voiceSave(String voiceText, String path) throws FailedVoiceSaveException {

		Calendar now = Calendar.getInstance(); // インスタンス化
		int y = now.get(Calendar.YEAR); // 年を取得
		int mo = now.get(Calendar.MONTH);// 月を取得
		int d = now.get(Calendar.DATE); // 現在の日を取得
		int h = now.get(Calendar.HOUR_OF_DAY);// 時を取得
		int m = now.get(Calendar.MINUTE); // 分を取得
		int s = now.get(Calendar.SECOND); // 秒を取得
		String buf = String.format("%1$04d", y) + String.format("%1$02d", mo + 1) + String.format("%1$02d", d) + String.format("%1$02d", h) + String.format("%1$02d", m) + String.format("%1$02d", s);

		int hWnd = this.win.getClassHWND("WindowsForms10.RichEdit20W.app.0.378734a", null);
		for (int i = 0; i < voiceText.length(); i++)
			OS.SendMessage(hWnd, OS.WM_CHAR, voiceText.charAt(i), 0);

		File file = new File("");

		try {
			String command = "java -jar " + getOpenSaveWindowPath() + "/OpenSaveWindow.jar " + castID;
			System.out.println(command);
			Runtime.getRuntime().exec(command);

			file = new File(path + "\\" + buf + "_" + voiceText + ".wav");
			command = "java -jar " + getVoiceSavePath() + "/VoiceSave.jar " + file.toString();
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(command);
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			ArrayList<String> ret = new ArrayList<String>();
			String str;
			while ((str = br.readLine()) != null) {
				ret.add(str);
			}
			System.out.println(ret.get(ret.size() - 2));
			if (!"0".equals(ret.get(ret.size() - 1))) {
				throw new FailedVoiceSaveException("音声の保存に失敗しました。");
			}
		} catch (IOException e) {
			throw new FailedVoiceSaveException("音声の保存に失敗しました。");
		}

		hWnd = this.win.getClassHWND("WindowsForms10.RichEdit20W.app.0.378734a", null);
		for (int i = 0; i < voiceText.length(); i++)
			OS.SendMessage(hWnd, OS.WM_KEYDOWN, OS.VK_BACK, 0);

		win.showWindow(OS.SW_MINIMIZE);

		return file;
	}

	public void voiceListen(String voiceText) {

		int hWnd = this.win.getClassHWND("WindowsForms10.RichEdit20W.app.0.378734a", null);
		for (int i = 0; i < voiceText.length(); i++)
			OS.SendMessage(hWnd, OS.WM_CHAR, voiceText.charAt(i), 0);

		hWnd = this.win.getClassHWND("WindowsForms10.BUTTON.app.0.378734a", " 再生");
		OS.SendMessage(hWnd, OS.BM_CLICK, 0, 0);

		hWnd = this.win.getClassHWND("WindowsForms10.RichEdit20W.app.0.378734a", null);
		for (int i = 0; i < voiceText.length(); i++)
			OS.SendMessage(hWnd, OS.WM_KEYDOWN, OS.VK_BACK, 0);

		win.showWindow(OS.SW_MINIMIZE);
	}

	public static String[] getCasts() {
		String str[] = {
				"さとうささら",
				"すずきつづみ",
				"タカハシ",
				"ONE",
				"結月ゆかり",
				"弦巻マキ",
				"東北ずん子",
				"琴葉茜",
				"琴葉葵",
				"吉田くん",
				"京町セイカ",
				"東北きりたん"
		};

		return str;
	}

	public static String getCast(int castID) {
		switch (castID) {
		case 0:
			return YUKARI_WINDOW_NAME;

		case 1:
			return MAKI_WINDOW_NAME;

		case 2:
			return ZUNKO_WINDOW_NAME;

		case 3:
			return AKANE_WINDOW_NAME;

		case 4:
			return AOI_WINDOW_NAME;

		case 5:
			return YOSHIDA_WINDOW_NAME;

		case 6:
			return SEIKA_WINDOW_NAME;

		case 7:
			return KIRITAN_WINDOW_NAME;

		default:
			return "";
		}
	}

	public void foreGround(int x, int y) {
		win.showWindow(OS.SW_RESTORE);
		OS.SetWindowPos(win.getTitleHWND(), 0, x, y, 442, 538, 0);
		OS.SetForegroundWindow(win.getTitleHWND());
	}

	public void setMinimize() {
		win.showWindow(OS.SW_MINIMIZE);
	}

	public String getVoiceSavePath() {
		return voiceSavePath;
	}

	public void setVoiceSavePath(String voiceSavePath) {
		this.voiceSavePath = voiceSavePath;
	}

	public String getOpenSaveWindowPath() {
		return openSaveWindowPath;
	}

	public void setOpenSaveWindowPath(String openSaveWindowPath) {
		this.openSaveWindowPath = openSaveWindowPath;
	}

}
