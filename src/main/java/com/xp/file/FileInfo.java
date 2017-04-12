package com.xp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class FileInfo {

	public static final String DIRECTORY = "directory";
	public static final String UNKNOW = "unknow";

	public static String getFileType(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		if (Files.isDirectory(path)) {
			return DIRECTORY;
		} else {
			String probeContentType = Files.probeContentType(path);
			if (probeContentType != null) {
				return probeContentType;
			} else {
				return UNKNOW;
			}
		}

	}

	public static long getFileCreateTime(String filePath) throws IOException {
		FileTime attribute = (FileTime) Files.getAttribute(Paths.get(filePath), "creationTime");
		return attribute.toMillis();
	}

	public static long getFileLastModifiedTime(String filePath) throws IOException {
		FileTime attribute = (FileTime) Files.getAttribute(Paths.get(filePath), "lastModifiedTime");
		return attribute.toMillis();
	}

	public static long getFileLastAccessTime(String filePath) throws IOException {
		FileTime attribute = (FileTime) Files.getAttribute(Paths.get(filePath), "lastAccessTime");
		return attribute.toMillis();
	}

	public static long getFileSize(String filePath) throws IOException {
		return Paths.get(filePath).toFile().length();
	}

}
