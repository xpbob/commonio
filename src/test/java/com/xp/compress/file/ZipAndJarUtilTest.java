package com.xp.compress.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class ZipAndJarUtilTest {

	@Test
	public void testAddFileToCompressFile() throws IOException {
		File fileToAdd = new File("d:/hhhh.txt");
		File zipFile = new File("d:/hhhh.zip");
		ZipAndJarUtil.addFileToCompressFile(zipFile, fileToAdd, "/home");
	}

	@Test
	public void testGetInputStream() throws IOException {
		File zipFile = new File("d:/hhhh.zip");
		ZipAndJarUtil.getInputStream(zipFile, "/home/hhhh.txt", new InputStreamHandler() {

			@Override
			public void getInputStream(InputStream in) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));) {
					String line = null;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

}
