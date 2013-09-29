package io;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Output {

	private PrintWriter printWriter;

	public Output(OutputStream out) {

		printWriter = new PrintWriter(out, true);
	}

	public void writeLine(String string) {

		printWriter.println(string);
	}

	public void close() {

		printWriter.close();
	}

}
