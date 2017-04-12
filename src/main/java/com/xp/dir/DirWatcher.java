package com.xp.dir;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

public class DirWatcher {
	private WatchService watcher;
	private boolean isAddCreateDir;
	private Kind<?>[] events;
	private CopyOnWriteArraySet<String> excludeDir = new CopyOnWriteArraySet<String>();
	private CopyOnWriteArraySet<String> excludeFile = new CopyOnWriteArraySet<String>();;

	private WatcherResultHandler resultHander;

	private volatile boolean isProcess = true;

	public DirWatcher() throws IOException {
		this(false);
	}

	public DirWatcher(boolean isAddCreateDir) throws IOException {
		watcher = FileSystems.getDefault().newWatchService();
		this.isAddCreateDir = isAddCreateDir;

	}

	public DirWatcher registerAllEvents(Path dir) throws IOException {
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		this.events = new Kind<?>[] { ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY };
		return this;

	}

	public DirWatcher registerCreateEvent(Path dir) throws IOException {
		dir.register(watcher, ENTRY_CREATE);
		this.events = new Kind<?>[] { ENTRY_CREATE };
		return this;

	}

	public DirWatcher registerDeleteEvent(Path dir) throws IOException {
		dir.register(watcher, ENTRY_DELETE);
		this.events = new Kind<?>[] { ENTRY_DELETE };
		return this;
	}

	public DirWatcher registerModifyEvent(Path dir) throws IOException {
		dir.register(watcher, ENTRY_MODIFY);
		this.events = new Kind<?>[] { ENTRY_MODIFY };
		return this;
	}

	public DirWatcher register(Path dir, Kind<?>... events) throws IOException {
		dir.register(watcher, events);
		this.events = events;
		return this;
	}

	private DirWatcher registerAll(final Path start, final Kind<?>... events) throws IOException {
		this.events = events;
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				register(dir, events);
				return FileVisitResult.CONTINUE;
			}
		});
		return this;
	}

	private <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	public void processEvents() throws Exception {

		while (true) {
			WatchKey key = watcher.take();
			if (!isProcess) {
				break;
			}
			Path dir = (Path) key.watchable();
			if (!excludeDir.isEmpty() && excludeDir.contains(dir.toString())) {
				key.cancel();
			} else {
				for (WatchEvent<?> event : key.pollEvents()) {
					Kind<?> kind = event.kind();
					if (kind == OVERFLOW) {
						continue;
					}
					WatchEvent<Path> ev = cast(event);
					Path name = ev.context();
					Path child = dir.resolve(name);
					if (isAddCreateDir && kind == ENTRY_CREATE) {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child, events);
						}
					}
					if (resultHander != null) {
						if (excludeFile.isEmpty() || !excludeFile.contains(child.toString())) {
							resultHander.handleResult(child, kind);
						}
					}

				}
				key.reset();
			}

		}

	}

	public void cancleDir(Collection<String> excludeDirs) {
		this.excludeDir.addAll(excludeDirs);
	}

	public void cancleDir(String excludeDir) {
		this.excludeDir.add(excludeDir);
	}

	public void cancleFile(Collection<String> excludeFiles) {
		this.excludeFile.addAll(excludeFiles);
	}

	public void cancleFile(String excludeFile) {
		this.excludeFile.add(excludeFile);
	}

	public void stopProcess() {
		isProcess = false;
	}

	public DirWatcher addHandler(WatcherResultHandler resultHander) {
		this.resultHander = resultHander;
		return this;
	}

}
