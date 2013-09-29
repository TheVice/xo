package test;

import org.junit.Test;
import org.junit.Assert;

import io.Output;
import java.io.ByteArrayOutputStream;

public class OutputTest extends Assert {

	@Test
	public void output() {

		ByteArrayOutput bao = getByteArrayOutputInstance(1024);
		bao.writeLine("Hello");
		assertEquals("Hello\r\n", bao.toString());
		bao.close();
		bao.writeLine("Hi");
		assertEquals("Hello\r\n", bao.toString());
		bao = getByteArrayOutputInstance(1024);
		bao.writeLine("Line1");
		bao.writeLine("Line2");
		assertEquals("Line1\r\nLine2\r\n", bao.toString());
		bao.close();
		assertEquals("Line1\r\nLine2\r\n", bao.toString());
	}

	public ByteArrayOutput getByteArrayOutputInstance(int n) {

		ByteArrayOutputStream stringBufferOutputStream = null;
		try {

			stringBufferOutputStream = new ByteArrayOutputStream(n);
		} catch (Exception exc) {

		}
		return new ByteArrayOutput(stringBufferOutputStream);
	}

	private class ByteArrayOutput extends Output {

		private ByteArrayOutputStream byteArrayOutputStream;

		private ByteArrayOutput(ByteArrayOutputStream stringBufferOutputStream) {

			super(stringBufferOutputStream);
			this.byteArrayOutputStream = stringBufferOutputStream;
		}

		@Override
		public String toString() {

			return byteArrayOutputStream.toString();
		}

		@Override
		public void close() {

			try {

				byteArrayOutputStream.close();
			} catch (Exception exc) {

			} finally {

				super.close();
			}
		}

	}

}
