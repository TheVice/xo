import org.junit.Test;
import org.junit.Assert;

import io.Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OutputTest extends Assert {

    @Test
    public void output() {

        final String lineSeparator = System.getProperty("line.separator");
        ByteArrayOutput bao = getByteArrayOutputInstance(0);
        bao.writeLine("Hello");
        assertEquals("Hello" + lineSeparator, bao.toString());
        bao.close();
        bao.writeLine("Hi");
        assertEquals("Hello" + lineSeparator, bao.toString());
        bao = getByteArrayOutputInstance(32);
        bao.writeLine("Line1");
        bao.writeLine("Line2");
        assertEquals("Line1" + lineSeparator + "Line2" + lineSeparator,
                bao.toString());
        bao.close();
        assertEquals("Line1" + lineSeparator + "Line2" + lineSeparator,
                bao.toString());
        bao = getByteArrayOutputInstance(32);
        bao.write("Line");
        assertEquals("Line", bao.toString());
        bao.close();
    }

    public static ByteArrayOutput getByteArrayOutputInstance(int n)
            throws IllegalArgumentException {

        ByteArrayOutputStream stringBufferOutputStream = (n > 0) ? new ByteArrayOutputStream(
                n) : new ByteArrayOutputStream();
        return new ByteArrayOutput(stringBufferOutputStream);
    }

    private static class ByteArrayOutput extends Output {

        private final ByteArrayOutputStream byteArrayOutputStream;

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
            } catch (IOException exc) {

            } finally {

                super.close();
            }
        }

    }

}
