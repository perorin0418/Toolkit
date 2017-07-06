package org.net.perorin.toolkit;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotUtils extends Robot {

	private RobotUtils() throws AWTException {
		super();
	}

	public static Robot init() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return robot;
	}

	public static void wait(int ms){
		Robot robot = init();
		robot.delay(ms);
	}

	public static Point getMousePos() {
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		return p;
	}

	public static void move(Point p) {
		Robot robot = init();
		robot.mouseMove(p.x, p.y);
	}

	public static void enter() {
		Robot robot = init();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(10);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void clickLeft() {
		Robot robot = init();
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(10);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public static void clickLeft(Point p) {
		move(p);
		clickLeft();
	}

	public static void doubleClickLeft() {
		Robot robot = init();
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(10);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(10);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public static void doubleClickLeft(Point p) {
		move(p);
		doubleClickLeft();
	}

	public static void dragAndDrop(Point dragPoint, Point dropPoint) {
		Robot robot = init();
		move(dragPoint);
		robot.mousePress(InputEvent.BUTTON1_MASK);

		for (int x = dragPoint.x; x != dropPoint.x;) {
			move(new Point(x, dragPoint.y));
			System.out.println(new Point(x, dragPoint.y));
			if (dragPoint.x < dropPoint.x) {
				x++;
			} else {
				x--;
			}
		}
		for (int y = dragPoint.y; y != dropPoint.y;) {
			move(new Point(dropPoint.x, y));
			System.out.println(new Point(dropPoint.x, y));
			if (dragPoint.y < dropPoint.y) {
				y++;
			} else {
				y--;
			}
		}

		move(dropPoint);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void typeNum(double n) {
		Robot robot = init();
		String buf = String.valueOf(n);
		for (int i = 0; i < buf.length(); i++) {
			if (buf.charAt(i) == '0') {
				robot.keyPress(KeyEvent.VK_0);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_0);
			} else if (buf.charAt(i) == '1') {
				robot.keyPress(KeyEvent.VK_1);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_1);
			} else if (buf.charAt(i) == '2') {
				robot.keyPress(KeyEvent.VK_2);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_2);
			} else if (buf.charAt(i) == '3') {
				robot.keyPress(KeyEvent.VK_3);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_3);
			} else if (buf.charAt(i) == '4') {
				robot.keyPress(KeyEvent.VK_4);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_4);
			} else if (buf.charAt(i) == '5') {
				robot.keyPress(KeyEvent.VK_5);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_5);
			} else if (buf.charAt(i) == '6') {
				robot.keyPress(KeyEvent.VK_6);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_6);
			} else if (buf.charAt(i) == '7') {
				robot.keyPress(KeyEvent.VK_7);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_7);
			} else if (buf.charAt(i) == '8') {
				robot.keyPress(KeyEvent.VK_8);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_8);
			} else if (buf.charAt(i) == '9') {
				robot.keyPress(KeyEvent.VK_9);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_9);
			} else if (buf.charAt(i) == '.') {
				robot.keyPress(KeyEvent.VK_PERIOD);
				robot.delay(10);
				robot.keyRelease(KeyEvent.VK_PERIOD);
			}
		}
	}
}
