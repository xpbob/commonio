package com.xp.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FileUtilsTest {

	/**
	 * time 91087 1.16G
	 * 
	 * @throws IOException
	 */
	@Test
	public void testFileCopy() throws IOException {
		Path s = Paths.get("F:/���ֺ�����.rmvb");
		Path t = Paths.get("d:/���ֺ�����.rmvb");
		long start = System.currentTimeMillis();
		FileUtils.copyFile(s, t);
		System.out.println(System.currentTimeMillis() - start);
	}

	/**
	 * time 50184 1.16G
	 * 
	 * @throws IOException
	 */
	@Test
	public void testFileCopyWithNIO() throws IOException {
		Path s = Paths.get("F:/���ֺ�����.rmvb");
		Path t = Paths.get("d:/���ֺ�����.rmvb");
		long start = System.currentTimeMillis();
		FileUtils.copyBigFile(s, t);
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void testAppendFile() throws IOException {
		Path s = Paths.get("d:/hello.txt");
		Path t = Paths.get("D:/thread.txt");
		FileUtils.appendBigFile(s, t);

	}

	@Test
	public void testCopyDirectory() throws IOException {
		Path s = Paths.get("d:/book");
		Path t = Paths.get("e:/xxx");
		FileUtils.copyDirectory(s, t);
	}

	@Test
	public void testDeleteDirectory() throws IOException {
		Path s = Paths.get("k:/5");
		FileUtils.deleteDirectory(s);
	}

	@Test
	public void testFilterFile() throws IOException {
		FileFilter filter = new FileFilter(null, "java", null, null);
		Path path = Paths.get("d:/testfile");
		FileUtils.filterFile(path, filter, new CommonPathHandler() {

			@Override
			public void getPath(Path path) {
				System.out.println(path.toString());
			}
		});
	}

}
