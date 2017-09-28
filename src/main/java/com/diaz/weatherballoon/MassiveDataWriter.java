package com.diaz.weatherballoon;

import java.io.IOException;

public interface MassiveDataWriter {

	void prepareToWrite() throws IOException;

	void writeLine(String line) throws IOException;

	void finishedWriting() throws IOException;

}