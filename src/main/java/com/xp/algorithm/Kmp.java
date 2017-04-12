package com.xp.algorithm;

public class Kmp {

	private Integer[] matchingStringTable;
	private char[] matchingCharArray;

	public Kmp(String matchingString) {
		if (matchingString == null) {
			throw new RuntimeException("No matchingString input");
		}
		matchingStringTable = new Integer[matchingString.length()];
		matchingCharArray = matchingString.toCharArray();
		getTableData();
	}

	private void getTableData() {
		matchingStringTable[0] = 0;
		char[] charArray = matchingCharArray;

		for (int i = 1, k = 0; i < charArray.length; i++) {
			while (k > 0 && charArray[k] != charArray[i]) {
				k = matchingStringTable[k - 1];
			}
			if (charArray[k] == charArray[i]) {
				k++;
			}
			matchingStringTable[i] = k;
		}
	}

	public int kmpIndexOf(String data) {
		int location = -1;
		if (data == null || data.length() < matchingStringTable.length) {
			return location;
		}
		char[] dataArray = data.toCharArray();
		char[] matchingCharArray = this.matchingCharArray;
		Integer[] nextTable = matchingStringTable;
		for (int i = 0, j = 0; i < dataArray.length; i++) {
			while (j > 0 && matchingCharArray[j] != dataArray[i]) {
				j = nextTable[j - 1];
			}
			if (matchingCharArray[j] == dataArray[i]) {
				j++;
			}
			if (matchingCharArray.length == j) {
				return i - j + 1;
			}
		}

		return location;
	}
}
