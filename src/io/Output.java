package io;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Output {

	private final PrintWriter printWriter;

	public Output(OutputStream out) {

		printWriter = new PrintWriter(out, true);
	}

	public void write(String string) {

		printWriter.print(string);
		printWriter.flush();
	}

	public void writeLine(String string) {

		printWriter.println(string);
	}

	public void close() {

		printWriter.close();
	}

}
