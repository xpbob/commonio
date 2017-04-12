package com.xp.dir;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;

import org.junit.Test;

public class DirWatcherTest {

	@Test
	public void testFile() throws Exception {
		final DirWatcher dw = new DirWatcher(true);
		dw.registerAllEvents(Paths.get("c:/hello")).addHandler(new WatcherResultHandler() {

			public void handleResult(Path path, Kind<?> event) {
				System.out.println(path.toString() + ": " + event.toString());
				// dw.cancleDir(path.getParent().toString());
				// dw.cancleFile(path.toString());
				// dw.stopProcess();
			}
		}).processEvents();

	}

}
