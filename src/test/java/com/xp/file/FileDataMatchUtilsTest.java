package com.xp.file;

import org.junit.Test;

public class FileDataMatchUtilsTest {

	/**
	 * hello.txt
	 * xxxxxhello--->true
	 * ------------------------------
	 * xxxxhell
	 * o----------->false
	 * @throws Exception
	 */
	@Test
	public void testWithLine() throws Exception {
		System.out.println(FileDataMatchUtils.matchWithLine("e:/hello.txt", "hello"));
	}

	/**
	 * hello.txt
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
	 * @throws Exception
	 */
	@Test
	public void testWithText() throws Exception {
		System.out.println(FileDataMatchUtils.matchWithText("e:/hello.txt", "hello"));
	}

}
