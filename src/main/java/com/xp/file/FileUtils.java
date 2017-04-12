package com.xp.file;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {

	private static final long SMALL_FILE_LENGTH = 100 * 1024;

	public static void copyFile(Path source, Path target) throws IOException {
		copyFile(source, target, true);
	}

	public static void copyFile(Path source, Path target, boolean preserve) throws IOException {
		CopyOption[] options = (preserve) ? new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING }
				: new CopyOption[] { REPLACE_EXISTING };
		if (Files.notExists(target)) {
			Files.copy(source, target, options);
		} else {
			throw new RuntimeException("Target file Exists");
		}
	}

	public static void copyBigFile(Path source, Path target) throws IOException {
		if (Files.isDirectory(source)) {
			throw new IOException("The source is directory");
		}
		try (FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);
				FileChannel targetChannel = FileChannel.open(target, StandardOpenOption.CREATE,
						StandardOpenOption.WRITE)) {
			targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		}

	}

	public static void appendBigFile(Path appendFile, Path target) throws IOException {
		if (Files.isDirectory(appendFile)) {
			throw new IOException("The source is directory");
		}

		if (Files.isDirectory(target)) {
			throw new IOException("The target is directory");
		}

		if (Files.notExists(target)) {
			throw new IOException("The" + target.toString() + " is not exists");
		}

		if (Files.notExists(appendFile)) {
			throw new IOException("The" + appendFile.toString() + " is not exists");
		}

		try (FileChannel sourceChannel = FileChannel.open(appendFile, StandardOpenOption.READ);
				FileChannel targetChannel = FileChannel.open(target, StandardOpenOption.APPEND)) {
			targetChannel.transferFrom(sourceChannel, targetChannel.size(), sourceChannel.size());

		}

	}

	/**
	 * check the path which is directory
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private static void checkIsDirectory(Path dir) throws IOException {
		if (!Files.isDirectory(dir)) {
			throw new IOException(dir.toString() + " is not a  directory");
		}

	}

	private static void checkIsExists(Path path) throws IOException {
		if (!Files.exists(path)) {
			throw new IOException(path.toString() + " is not exists");
		}
	}

	public static void copyFileSmartly(Path source, Path target) throws IOException {
		if (source.toFile().length() > SMALL_FILE_LENGTH) {
			copyBigFile(source, target);
		} else {
			copyFile(source, target);
		}
	}

	public static void copyDirectory(Path source, Path target) throws IOException {
		checkIsDirectory(source);
		checkIsExists(source);
		TreeCopier copier = new TreeCopier(source, target);
		Files.walkFileTree(source, copier);
	}

	public static void deleteDirectory(Path deletedPath) throws IOException {
		checkIsDirectory(deletedPath);
		checkIsExists(deletedPath);
		TreeDeleter deleter = new TreeDeleter();
		Files.walkFileTree(deletedPath, deleter);
	}

	public static void filterFile(Path path, FileFilter filter, CommonPathHandler handler) throws IOException {
		checkIsDirectory(path);
		checkIsExists(path);
		CommonFileVisitor vister = new CommonFileVisitor(filter, handler);
		Files.walkFileTree(path, vister);
	}

}
