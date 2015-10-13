import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;

import io.Input;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InputTest extends Assert {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void input() throws IOException {

        final String lineSeparator = System.getProperty("line.separator");
        String str = "Line1" + lineSeparator + "Line2" + lineSeparator
                + "Line3";

        ByteArrayInput byteInput = getByteInputInstance(str.getBytes());

        assertEquals("Line1", byteInput.readLine());
        byteInput.close();

        exception.expect(IOException.class);
        byteInput.readLine();

        byteInput = getByteInputInstance(str.getBytes());
        assertEquals("Line1", byteInput.readLine());
        assertEquals("Line2", byteInput.readLine());
        assertEquals("Line3", byteInput.readLine());
        assertEquals(null, byteInput.readLine());
    }

    public static ByteArrayInput getByteInputInstance(byte inputBuffer[]) {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                inputBuffer);
        return new ByteArrayInput(byteArrayInputStream);
    }

    private static class ByteArrayInput extends Input {

        private final ByteArrayInputStream byteArrayInputStream;

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
