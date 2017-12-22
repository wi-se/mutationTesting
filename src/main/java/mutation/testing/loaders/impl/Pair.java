package mutation.testing.loaders.impl;

import java.io.File;

public class Pair {
	private File file;
	private String attachedPath;
	private String attachedClassTest;

	public String getAttachedClassTest() {
		return attachedClassTest;
	}

	public void setAttachedClassTest(String attachedClassTest) {
		this.attachedClassTest = attachedClassTest;
	}

	public Pair(File file, String str) {
		this.file = file;
		this.attachedPath = str;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getAttachedPath() {
		return attachedPath;
	}

	public void setAttachedPath(String attachedPath) {
		this.attachedPath = attachedPath;
	}

}
