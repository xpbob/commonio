package com.xp.dir;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DiskUtil {

	public static long getAvailByte(String path) throws IOException {
		FileStore fileStore = Files.getFileStore(Paths.get(path));
		return fileStore.getUnallocatedSpace();
	}

	public static long getUsedByte(String path) throws IOException {
		FileStore fileStore = Files.getFileStore(Paths.get(path));
		return fileStore.getTotalSpace() - fileStore.getUnallocatedSpace();
	}

	
	public static Map<String, FileStore> getSystemFileStore() {
		Map<String, FileStore> fileSystemMap = new HashMap<String, FileStore>();
		FileSystem system = FileSystems.getDefault();
		Iterable<FileStore> fileStores = system.getFileStores();
		for (FileStore store : fileStores) {
			fileSystemMap.put(store.toString(), store);
		}
		return fileSystemMap;
	}
}
