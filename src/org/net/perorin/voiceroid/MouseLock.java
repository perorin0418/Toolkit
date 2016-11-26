package org.net.perorin.voiceroid;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import org.eclipse.swt.internal.win32.OS;

public class MouseLock {

	private static Locker locker = null;

	public static void run() {
		if (locker == null) {
			locker = new Locker();
		}
		locker.setDaemon(true);
		locker.start();
	}

	public static void stop() {
		if (locker != null) {
			locker.notifyStop();
			locker.interrupt();
		}
		try {
			locker.join(1000);
		} catch (InterruptedException e) {
		}
		locker = null;
	}

	public static void setPos(Point p) {
		if (locker != null) {
			locker.setPos(p);
			locker.interrupt();
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}
}

class Locker extends Thread {
	private boolean loopFlag = true;
	private Point pos;

	public Locker() {
		PointerInfo pi = MouseInfo.getPointerInfo();
		pos = pi.getLocation();
	}

	public void notifyStop() {
		loopFlag = false;
	}

	public void run() {
		while (loopFlag) {
			OS.SetCursorPos(pos.x, pos.y);
		}
	}

	public void setPos(Point p) {
		this.pos = p;
	}
}