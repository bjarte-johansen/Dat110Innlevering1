package utils;

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Locale;

import utils.Debug;
/*
public class IndentedOut extends PrintStream {
    public IndentedOut(OutputStream out) {
        super(out);
    }

    @Override
    public void println(String x) {
        Debug.println(x);
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        Debug.printf(format, args);
        return this;
    }

    // fallback for println(any)
    @Override
    public void println(Object x) {
        Debug.println(String.valueOf(x));
    }
}
*/