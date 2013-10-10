package test;

import org.junit.Assert;
import org.junit.Test;

import io.Input;

import java.io.InputStream;
import java.io.StringBufferInputStream;

public class InputTest extends Assert {

	@Test
	public void input() {

		String lineSeparator = System.getProperty("line.separator");
		String str = "Line1" + lineSeparator + "Line2" + lineSeparator
				+ "Line3";
		StringInput strInput = getStringInputInstance(str);

		assertEquals("Line1", strInput.readLine());
		strInput.close();
		assertEquals(null, strInput.readLine());

		strInput = getStringInputInstance(str);
		assertEquals("Line1", strInput.readLine());
		assertEquals("Line2", strInput.readLine());
		assertEquals("Line3", strInput.readLine());
		assertEquals(null, strInput.readLine());
		strInput.close();
	}

	public static StringInput getStringInputInstance(String inputString) {

		StringBufferInputStream stringBufferInputStream = new StringBufferInputStream(
				inputString);
		return new StringInput(stringBufferInputStream);
	}

	@SuppressWarnings("deprecation")
	private static class StringInput extends Input {

		private StringBufferInputStream stringBufferInputStream;

		private StringInput(StringBufferInputStream stringBufferInputStream) {

			super(stringBufferInputStream);
			this.stringBufferInputStream = stringBufferInputStream;
		}

		@Override
		public void close() {

			try {

				stringBufferInputStream.close();
			} catch (Exception exc) {

			} finally {

				super.close();
			}
		}

	}

}
