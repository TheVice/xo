package io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Input {

	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;

	public Input(InputStream in) {

		try {

			inputStreamReader = new InputStreamReader(in);
		} catch (Exception exc) {

		}

		try {

			bufferedReader = new BufferedReader(inputStreamReader);
		} catch (Exception exc) {

		}

	}

	public String readLine() {

		String string = null;

		try {

			if (bufferedReader != null) {

				string = bufferedReader.readLine();
			}
		} catch (Exception exc) {

		}

		return string;
	}

	public void close() {

		try {

			if (bufferedReader != null) {

				bufferedReader.close();
			}
		} catch (Exception exc) {

		} finally {

			try {

				if (inputStreamReader != null) {

					inputStreamReader.close();
				}
			} catch (Exception exc) {

			}
		}
	}

}
