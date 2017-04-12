package com.xp.algorithm;

import org.junit.Test;

public class KmpTest {

	@Test
	public void test() {
		String data = "xyxababcaxxxababca";
		String matchString = "ababca";
		Kmp kmp =new Kmp(matchString);
		System.out.println(kmp.kmpIndexOf(data));
	}

}
