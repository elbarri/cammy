package com.diaz.weatherballoon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BalloonDataWriter implements MassiveDataWriter{

	private BufferedWriter bufferedWriter;
	String fileName;
	
	public BalloonDataWriter(String fileName) {
		this.fileName = fileName;
	}

	public void prepareToWrite() throws IOException {
		File file = new File(fileName);
		bufferedWriter = new BufferedWriter(new FileWriter(file));
	}

	public void writeLine(String line) throws IOException {
		bufferedWriter.write(line);
	}

	public void finishedWriting() throws IOException {
		bufferedWriter.close();
//		file.delete();
	}
}
