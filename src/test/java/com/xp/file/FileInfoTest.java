package com.xp.file;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class FileInfoTest {

	@Test
	public void testFileType() throws IOException {
		String fileType = FileInfo.getFileType("C:/hello/x.html");
		System.out.println(fileType);
	}
	@Test
	public void testFileCreateTime() throws IOException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sf.format(new Date(FileInfo.getFileCreateTime("C:/hello/x.html"))));
		System.out.println(sf.format(new Date(FileInfo.getFileLastModifiedTime("C:/hello/x.html"))));
		System.out.println(sf.format(new Date(FileInfo.getFileLastAccessTime("C:/hello/x.html"))));
		System.out.println(FileInfo.getFileSize("C:/hello/x.html"));
	}

}
