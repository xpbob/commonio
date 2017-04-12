package com.xp.file;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class TreeCopier extends SimpleFileVisitor<Path> {

	private Path source;
	private Path target;

	public TreeCopier(Path source, Path target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		CopyOption[] options = new CopyOption[] { COPY_ATTRIBUTES };
		Path newDir = target.resolve(source.relativize(dir));
		try {
			Files.copy(dir, newDir, options);
		} catch (FileAlreadyExistsException x) {
			// ignore
			// directory is exists
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		FileUtils.copyFile(file, target.resolve(source.relativize(file)));
		return FileVisitResult.CONTINUE;
	}

}