package com.xp.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CommonFileVisitor extends SimpleFileVisitor<Path> {
	private FileFilter filter;
	private CommonPathHandler handler;

	public CommonFileVisitor(FileFilter filter, CommonPathHandler handler) {
		this.filter = filter;
		this.handler = handler;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (filter != null) {
			String fileName = file.getFileName().toString();
			if (filter.match(fileName)) {
				handler.getPath(file);
			}
		} else {
			handler.getPath(file);
		}
		return FileVisitResult.CONTINUE;
	}

}
