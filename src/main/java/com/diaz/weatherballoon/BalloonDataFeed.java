package com.diaz.weatherballoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BalloonDataFeed implements DataFeed {

	private BufferedReader bufferedReader;
	String fileName;
	
	public BalloonDataFeed(String fileName) {
		this.fileName = fileName;
	}

	public void prepareToRead() throws IOException {
		File file = new File(fileName);
		bufferedReader = new BufferedReader(new FileReader(file));
	}

	public String readLine() throws IOException {
		return bufferedReader.readLine();
	}

	public void finishedReading() throws IOException {
		bufferedReader.close();
//		file.delete();
	}

}
