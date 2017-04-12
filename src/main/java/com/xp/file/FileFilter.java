package com.xp.file;

public class FileFilter {
	private  String startWith;
	private  String endWith;
	private  String matches;
	private  String contains;

	
	
	public FileFilter(String startWith, String endWith, String matches, String contains) {
		this.startWith = startWith;
		this.endWith = endWith;
		this.matches = matches;
		this.contains = contains;
	}



	public  boolean match(String fileName) {
		if (startWith != null && !fileName.startsWith(startWith))
			return false;
		if (endWith != null && !fileName.endsWith(endWith))
			return false;
		if (matches != null && !fileName.matches(matches))
			return false;
		if (contains != null && !fileName.contains(contains))
			return false;
		return true;
	}
}
