package test;

import org.junit.Assert;
import org.junit.Test;

import io.Input;
import java.io.StringBufferInputStream;

public class InputTest extends Assert {

	@Test
	public void input() {

		String str = "Line1\r\nLine2\r\nLine3";
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

	public StringInput getStringInputInstance(String inputString) {

		StringBufferInputStream stringBufferInputStream = new StringBufferInputStream(
				inputString);
		return new StringInput(stringBufferInputStream);
	}

	@SuppressWarnings("deprecation")
	private class StringInput extends Input {

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
