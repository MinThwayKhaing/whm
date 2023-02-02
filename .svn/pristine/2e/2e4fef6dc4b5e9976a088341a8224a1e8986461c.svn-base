package com.dat.whm.web.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Scheduler {
	private String dirPath;
	private Timer timer;
	
	public Scheduler(String dirPath) {
		this.dirPath = dirPath;
	    timer = new Timer();
	    timer.schedule(new RemindTask(), 30 * 1000 * 60, 30 * 1000 * 60);
	}
	
	public List<File> load() {
		List<File> filePathList = new ArrayList<File>();
		File[] listOfFiles = new File(dirPath).listFiles();
		for(File file : listOfFiles) {
			String ext = FilenameUtils.getExtension(file.getName());
			if ("pdf".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "html".equalsIgnoreCase(ext)) {
				filePathList.add(file);
			}
		}
		return filePathList;
	}
	
	private class RemindTask extends TimerTask {
	    public void run() {
	    	System.out.println("Directory Cleanner has bean started...");
	    	List<File> filePathList = load();
	    	for(File file : filePathList) {
	    		String fileName = file.getName();
	    		int start = 0;
	    		int end = fileName.indexOf(".");
	    		String time = fileName.substring(start, end);
	    		System.out.println(time);
	    		long miliSec = Long.parseLong(time);
	    		Date createDate = new Date(miliSec);
	    		Calendar cal = Calendar.getInstance();
	    		cal.add(Calendar.MINUTE, -30);
	    		Date currentDate = cal.getTime();
	    		if(createDate.before(currentDate)) {
	    			try {
						FileUtils.forceDelete(file);
						System.out.println(fileName + " has been deleted.");
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}
	    	}
	    }
	}
	
	public static void main(String[] args) {
		String dirPath = "C:\\temp";
		new Scheduler(dirPath);
	}
}
