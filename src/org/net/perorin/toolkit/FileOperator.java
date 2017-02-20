package org.net.perorin.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/***
 * ファイル操作に関する操作をまとめたもの
 * @author perorin
 *
 */
public class FileOperator {

	/***
	 * ディレクトリのみを選択するモード
	 */
	public static final int DIRECTORIES_ONLY = JFileChooser.DIRECTORIES_ONLY;

	/***
	 * ファイルのみを選択するモード
	 */
	public static final int FILES_ONLY = JFileChooser.FILES_ONLY;

	/***
	 * ディレクトリとファイルを選択するモード
	 */
	public static final int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;

	/***
	 * 拡張子を表示しないモード
	 */
	public static final int NO_EXTENSION = 0;

	/***
	 * 拡張子を表示するモード
	 */
	public static final int WITH_EXTENSION = 1;

	private FileOperator() {
	}

	/***
	 * ファイルパス（絶対パスも相対パスも可）のファイル名のみにする
	 * @param filePath
	 * @return ファイル名（拡張子を除く）
	 */
	public static String extractFilename(String filePath, int mode) {
		filePath = filePath.replaceAll("\\\\", "/");
		String[] strs1 = filePath.split("/");
		if (mode == FileOperator.NO_EXTENSION) {
			String[] strs2 = strs1[strs1.length - 1].split(Pattern.quote("."));
			if (strs2.length != 2) {
				return "";
			}
			return strs2[0];
		} else if (mode == FileOperator.WITH_EXTENSION) {
			return strs1[strs1.length - 1];
		}
		return "";
	}

	/***
	 * ファイルパス（絶対パスも相対パスも可）のファイル名のみにする
	 * @param path
	 * @return ファイル名（拡張子を除く）
	 */
	public static String extractFilename(File path, int mode) {
		String str = path.getAbsolutePath();
		str = str.replaceAll("\\\\", "/");
		String[] strs1 = str.split("/");
		String[] strs2 = strs1[strs1.length - 1].split(Pattern.quote("."));
		if (strs2.length != 2)
			return "";
		if (mode == FileOperator.NO_EXTENSION)
			return strs2[0];
		if (mode == FileOperator.WITH_EXTENSION)
			return strs1[strs1.length - 1];
		return "";
	}

	/***
	 * ファイル選択ウィンドウを表示して、選択したパスを返却する
	 * @param mode
	 * @return
	 */
	public static File getPath(String dialogTitle, int mode) {
		File file = new File("");

		JFileChooser fileChooser = new JFileChooser("./");

		fileChooser.setDialogTitle(dialogTitle);

		fileChooser.setFileSelectionMode(mode);

		JFrame frame = new JFrame();
		int selected = fileChooser.showOpenDialog(frame);
		if (selected == JFileChooser.APPROVE_OPTION)
			file = fileChooser.getSelectedFile();
		frame.dispose();

		return file;
	}

	/***
	 * 指定フォルダ内にあるファイル一覧をArrayListで返す
	 * @param path
	 * @return
	 */
	public static ArrayList<File> listFiles(File path) {
		ArrayList<File> list = new ArrayList<File>();

		File[] files = path.listFiles();
		for (File file : files) {
			if (!file.getName().equals("")) {
				list.add(file);
			}
		}
		return list;
	}

	public static void copyFile(File in, File out) throws Exception {
        FileInputStream fis  = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }

}
