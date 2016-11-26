package org.net.perorin.toolkit;

/***
 * 配列に関する操作をまとめたもの
 * @author perorin
 *
 */
public class ArrayOperator {

	private ArrayOperator() {
	}

	/***
	 * 配列arrayの中をすべて整数型numにする<br>
	 * @param array
	 * @param num
	 * @return すべての要素がnumの配列
	 */
	public static int[] fillInt(int[] array, int num) {
		for (int i = 0; i < array.length; i++) {
			array[i] = num;
		}
		return array;
	}

	/***
	 * int型配列array1とint型配列array2が完全に等しいどうか確認<br>
	 * @param array1
	 * @param array2
	 * @return 等しければtrue、そうでなければfalse
	 */
	public static boolean equals(int[] array1, int[] array2) {
		for (int i = 0; i < array1.length; i++)
			if (Logic.not(array1[i] == array2[i]))
				return false;
		return true;
	}

	/***
	 * 配列内の要素のインデックスを返却
	 * @param objs
	 * @param data
	 * @return 要素のインデックス番号、見つからなければ0
	 */
	public static int getIndex(Object[] objs, Object data) {
		for (int i = 0; i < objs.length; i++) {
			if (objs[i].equals(data)) {
				return i;
			}
		}
		return 0;
	}
}
