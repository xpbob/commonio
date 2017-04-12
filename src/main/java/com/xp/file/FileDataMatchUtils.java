package com.xp.file;

import java.io.BufferedReader;
import java.io.FileReader;

import com.xp.algorithm.Kmp;

public class FileDataMatchUtils {
	/**
	 * xxxxxhello--->true
	 * ------------------------------
	 * xxxxhell
	 * o----------->false
	 * @param file
	 * @param matchingStirng
	 * @return
	 * @throws Exception
	 */
	public static boolean matchWithLine(String file, String matchingStirng) throws Exception {
		Kmp kmp = new Kmp(matchingStirng);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				int kmpIndexOf = kmp.kmpIndexOf(readLine);
				if (kmpIndexOf > 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * xxxxxhello--->true
	 * ------------------------------
	 * xxxxhell
	 * o----------->true
	 * ------------------------------
	 * h
	 * e
	 * l
	 * l
	 * o----------->true
	 * @param file
	 * @param matchingStirng
	 * @return
	 * @throws Exception
	 */
	public static boolean matchWithText(String file, String matchingStirng) throws Exception {
		Kmp kmp = new Kmp(matchingStirng);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String readLine = null;
			String preLine = null;
			while ((readLine = br.readLine()) != null) {
				if (preLine != null) {
					//append line
					readLine = preLine + readLine;
				}
				if (readLine.length() < matchingStirng.length()) {
					preLine = readLine;
					continue;
				}
				int kmpIndexOf = kmp.kmpIndexOf(readLine);
				if (kmpIndexOf >= 0) {
					return true;
				}

				preLine = readLine.substring(readLine.length() - matchingStirng.length(), readLine.length());
			}
		}
		return false;
	}
}
