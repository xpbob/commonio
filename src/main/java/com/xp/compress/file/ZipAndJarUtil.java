package com.xp.compress.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class ZipAndJarUtil {

	private static final String ROOT_PATH = "/";

	/**
	 * locationToAdd is the path in compressFile,fileToAdd will add to
	 * locationToAdd
	 * 
	 * @param compressedFile
	 * @param fileToAdd
	 * @param locationToAdd
	 * @throws IOException
	 */
	public static void addFileToCompressFile(File compressedFile, File fileToAdd, String locationToAdd)
			throws IOException {
		try (FileSystem fs = getFileSystem(compressedFile)) {
			Path pathToAddFile = fileToAdd.toPath();
			Path location = fs.getPath(locationToAdd);
			if (!Files.exists(location)) {
				// create path when location is not exist
				Files.createDirectories(location);
			}
			Path pathInCompressedFile = fs.getPath(locationToAdd, fileToAdd.getName());
			Files.copy(pathToAddFile, pathInCompressedFile, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static void addFileToCompressFileRoot(File compressedFile, File fileToAdd) throws IOException {
		addFileToCompressFile(compressedFile, fileToAdd, ROOT_PATH);
	}

	public static void deleteFileFromCompressFile(File compressedFile, String locationToDelete) throws IOException {
		try (FileSystem fs = getFileSystem(compressedFile)) {
			Path location = fs.getPath(locationToDelete);
			Files.delete(location);
		}
	}

	public static void getInputStream(File compressedFile, String locationToGet, InputStreamHandler handler)
			throws IOException {
		try (FileSystem fs = getFileSystem(compressedFile)) {
			InputStream newInputStream = Files.newInputStream(fs.getPath(locationToGet));
			handler.getInputStream(newInputStream);
		}
	}

	private static FileSystem getFileSystem(File compressedFile) throws IOException {
		Map<String, String> env = new HashMap<String, String>();
		env.put("create", "true");
		return FileSystems.newFileSystem(URI.create("jar:" + compressedFile.toURI()), env);
	}

}
