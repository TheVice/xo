package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Input {

    private final InputStreamReader inputStreamReader;
    private final BufferedReader bufferedReader;

    public Input(InputStream in) {

        inputStreamReader = new InputStreamReader(in);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public String readLine() throws IOException {

        return bufferedReader.readLine();
    }

    public void close() throws IOException {

        bufferedReader.close();
        inputStreamReader.close();
    }

}
