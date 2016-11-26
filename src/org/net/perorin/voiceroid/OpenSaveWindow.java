package org.net.perorin.voiceroid;

import org.eclipse.swt.internal.win32.OS;
import org.net.perorin.exception.HwndNotFoundException;
import org.net.perorin.win32API.W32window;

public class OpenSaveWindow {

	private static W32window win;

	public static void main(String[] args) {
		int castID = Integer.parseInt(args[0]);
		try {
			try {
				win = new W32window(Voiceroid.getCast(castID));
			} catch (HwndNotFoundException e) {
				win = new W32window(Voiceroid.getCast(castID) + "*");
			}
		} catch (HwndNotFoundException e) {
			return;
		}

		int hWnd = win.getClassHWND("WindowsForms10.BUTTON.app.0.378734a", " 音声保存");
		OS.SendMessage(hWnd, OS.BM_CLICK, 0, 0);

	}
}
