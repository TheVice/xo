
import org.junit.Test;
import org.junit.Assert;

import io.Input;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InputTest extends Assert {

	@Test
	public void input() throws IOException {

		String lineSeparator = System.getProperty("line.separator");
		String str = "Line1" + lineSeparator + "Line2" + lineSeparator
				+ "Line3";

		ByteArrayInput byteInput = getByteInputInstance(str);

		assertEquals("Line1", byteInput.readLine());
		byteInput.close();
		assertEquals(null, byteInput.readLine());

		byteInput = getByteInputInstance(str);
		assertEquals("Line1", byteInput.readLine());
		assertEquals("Line2", byteInput.readLine());
		assertEquals("Line3", byteInput.readLine());
		assertEquals(null, byteInput.readLine());
	}

	public static ByteArrayInput getByteInputInstance(String inputString) {

		int i = 0;
		byte buffer[] = new byte[inputString.length()];
		for (char c : inputString.toCharArray()) {

			buffer[i++] = (byte) c;
		}

		return getByteInputInstance(buffer);
	}

	public static ByteArrayInput getByteInputInstance(byte inputBuffer[]) {

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				inputBuffer);
		return new ByteArrayInput(byteArrayInputStream);
	}

	private static class ByteArrayInput extends Input {

		private ByteArrayInputStream byteArrayInputStream;

		private ByteArrayInput(ByteArrayInputStream byteArrayInputStream) {

			super(byteArrayInputStream);
			this.byteArrayInputStream = byteArrayInputStream;
		}

		@Override
		public void close() throws IOException {

			byteArrayInputStream.close();
			super.close();
		}
	}

}
