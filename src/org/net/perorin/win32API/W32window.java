package org.net.perorin.win32API;

import java.util.ArrayList;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.net.perorin.exception.HwndNotFoundException;

public class W32window {

	private int titleHWND = 0;
	private int window = 0;

	/***
	 * デフォルトコンストラクタ
	 * @param title
	 */
	public W32window(String title) throws HwndNotFoundException {
		this.titleHWND = (int) OS.FindWindow(null, new TCHAR(OS.CP_INSTALLED, title, true));
		this.window = (int) OS.GetWindow(titleHWND, OS.GW_CHILD);

		if (getTitleHWND() == 0)
			throw new HwndNotFoundException("指定したタイトル[" + title + "]のハンドルが見つかりません");

	}

	/***
	 * ウィンドウハンドルを16進数表示で取得
	 * @return
	 */
	public String getHexTitleHWND() {
		return Integer.toHexString(this.titleHWND);
	}

	/***
	 * ウィンドウハンドルを取得
	 * @return
	 */
	public int getTitleHWND() {
		return titleHWND;
	}

	/***
	 * ハンドルからテキストを取得
	 * @param hWnd
	 * @return
	 */
	public String getWindowText(int hWnd) {
		TCHAR buf = new TCHAR(OS.CP_INSTALLED, 256);
		OS.GetWindowText(hWnd, buf, 256);
		return buf.toString(0, buf.strlen());
	}

	/***
	 * ウィンドウ部品のクラス名とタイトル名からクラスハンドルを取得
	 * @param cls
	 * @param title
	 * @return
	 */
	public int getClassHWND(String cls, String title) {

		int hWnd = 0;
		ArrayList<String> list = new ArrayList<String>();
		list.add(String.valueOf(OS.GetWindow(titleHWND, OS.GW_CHILD)));

		while (list.size() > 0) {
			int i = Integer.parseInt(list.get(list.size() - 1).toString());
			list.remove(list.size() - 1);
			TCHAR buf1 = new TCHAR(OS.CP_INSTALLED, 256);
			TCHAR buf2 = new TCHAR(OS.CP_INSTALLED, 256);
			OS.GetWindowText(i, buf1, 256);
			OS.GetClassName(i, buf2, 256);
			if (title != null) {
				if ((buf1.toString(0, buf1.strlen()).equals(title)) && (buf2.toString(0, buf2.strlen()).equals(cls))) {
					hWnd = i;
					System.out.println(Integer.toHexString(i) + " , " + getWindowText(i));
					break;
				}
			} else {
				if (buf2.toString(0, buf2.strlen()).equals(cls)) {
					hWnd = i;
					System.out.println(Integer.toHexString(i) + " , " + getWindowText(i));
					break;
				}
			}
			int c = (int) OS.GetWindow(i, OS.GW_CHILD);
			if (c == 0) {
				int n = (int) OS.GetWindow(i, OS.GW_HWNDNEXT);
				if (n == 0) {
					continue;
				} else {
					list.add(String.valueOf(n));
				}
			} else {
				int n = (int) OS.GetWindow(i, OS.GW_HWNDNEXT);
				if (n != 0)
					list.add(String.valueOf(n));
				list.add(String.valueOf(c));
			}
		}
		return hWnd;
	}

	/***
	 * ウィンドウの位置を設定
	 * @param pos
	 */
	public void setWindowPos(Position pos) {
		setWindowPos(pos.getX(), pos.getY());
	}

	/***
	 * ウィンドウの位置を設定
	 * @param x
	 * @param y
	 */
	public void setWindowPos(int x, int y) {
		Size size = getWindowSize();
		OS.SetWindowPos(getTitleHWND(), 0, x, y, size.getWidth(), size.getHeight(), 0);
	}

	/***
	 * ウィンドウの高さ、幅を設定
	 * @param size
	 */
	public void setWindowSize(Size size) {
		setWindowSize(size.getWidth(), size.getHeight());
	}

	/***
	 * ウィンドウの高さ、幅を設定
	 * @param width
	 * @param height
	 */
	public void setWindowSize(int width, int height) {
		Position pos = getWindowPos();
		OS.SetWindowPos(getTitleHWND(), 0, pos.getX(), pos.getY(), width, height, 0);
	}

	/***
	 * ウィンドウの位置を取得
	 * @return
	 */
	public Position getWindowPos() {
		RECT rec = new RECT();
		OS.GetWindowRect(titleHWND, rec);
		return new Position(rec.left, rec.top);
	}

	/***
	 * ウィンドウの幅、高さを取得
	 * @return
	 */
	public Size getWindowSize() {
		RECT rec = new RECT();
		OS.GetWindowRect(titleHWND, rec);
		return new Size(rec.right - rec.left, rec.bottom - rec.top);
	}

	/***
	 * ウィンドウを最前面に出す
	 */
	public void foreGround() {
		OS.SetForegroundWindow(titleHWND);
	}

	/***
	 * nCmdShowの取りうる値<br>
	 * SW_FORCEMINIMIZE	ウィンドウを所有するスレッドがハングしても、ウィンドウを最小化する。<br>
	 * SW_HIDE	ウィンドウを非表示にし、他のウィンドウをアクティブにする<br>
	 * SW_MAXIMIZE	ウィンドウを最大化する<br>
	 * SW_MINIMIZE	ウィンドウを最小化し、Zオーダーが次のトップレベルウィンドウをアクティブにする。<br>
	 * SW_RESTORE	ウィンドウをアクティブにして表示する。最大化または最小化されていたウィンドウは元の位置とサイズに戻る。<br>
	 * SW_SHOW	ウィンドウをアクティブにして、現在の位置とサイズで表示する。<br>
	 * SW_SHOWMAXIMIZED	ウィンドウを最大化する<br>
	 * SW_SHOWMINIMIZED	ウィンドウを最小化する<br>
	 * SW_SHOWMINNOACTIVE	ウィンドウを現在のサイズと位置で表示する。<br>
	 * SW_SHOWNORMAL	ウィンドウをアクティブにして表示する。初めてウィンドウを表示するときには、このフラグを指定せよ。<br>
	 * @param nCmdShow
	 */
	public void showWindow(int nCmdShow) {
		OS.ShowWindow(titleHWND, nCmdShow);
	}

}
