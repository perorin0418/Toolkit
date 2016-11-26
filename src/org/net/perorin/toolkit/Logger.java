package org.net.perorin.toolkit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	public static void error(String path, StackTraceElement[] stackTraceElements) {
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

			File out = new File(path + "report_" + sdf.format(c.getTime()) + ".log");
			FileWriter fw = new FileWriter(out);
			BufferedWriter bw = new BufferedWriter(fw);

			for (StackTraceElement ste : stackTraceElements) {
				System.out.println(ste);
				bw.write(ste.toString() + "\n");
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
