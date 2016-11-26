package org.net.perorin.voiceroid;

import org.eclipse.swt.internal.win32.OS;
import org.net.perorin.exception.HwndNotFoundException;
import org.net.perorin.toolkit.RobotOperator;
import org.net.perorin.win32API.W32window;

public class VoiceSave {
	public static void main(String[] args) {
		try {
			W32window w = null;

			for (int i = 0; i < 100; i++) {
				try {
					w = new W32window("音声ファイルの保存");
					break;
				} catch (HwndNotFoundException e) {
					RobotOperator.wait(100);
				}
			}

			int hWnd = w.getClassHWND("Edit", null);
			while (hWnd == 0) {
				hWnd = w.getClassHWND("Edit", null);
			}

			String voice_text = args[0];
			for (int i = 0; i < voice_text.length(); i++) {
				OS.SendMessage(hWnd, OS.WM_CHAR, voice_text.charAt(i), 0);
			}
			hWnd = w.getClassHWND("Button", "保存(&S)");
			OS.SendMessage(hWnd, OS.BM_CLICK, 0, 0);

			System.out.println(0);
		} catch (Exception e) {
			System.out.println(-1);
		}
	}

}
