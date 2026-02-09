package utils;

import java.io.PrintStream;
import java.util.Stack;

public final class Debug {
    protected static PrintStream __ORG_OSTREAM = System.out;

    private static String NEWLINE_CHAR = "\n";   // we dont use System.lineSeparator() because we want to control the newline character (for testing)

    private static int indent = 0;
    private static final String IND = "    "; // 4 spaces
    private static Stack<String> trace = new Stack<>();

    /**
     * Private constructor to prevent instantiation of the Debug class. This class is intended
     */
    private Debug() {}


    /**
     * create padding string
     */
    private static String pad() {
        return IND.repeat(Math.max(0, indent));
    }

    /**
     * change indent by n (positive or negative)
     */
    public static void indent(int n) {
        indent = Math.max(0, indent + n);
    }


    // change indent ...
    public static void indent() {
        Debug.indent(1);
    }

    // change indent ...
    public static void unindent() {
        Debug.indent(-1);
    }


    /*********************************************************/

    // print
    public static void print(String s) {
        __ORG_OSTREAM.print(pad() + s);
    }

    // println
    public static void println(String s) {
        __ORG_OSTREAM.print(pad() + s + NEWLINE_CHAR);
    }
    public static void println() {
        __ORG_OSTREAM.println();
    }

    // printf
    public static void printf(String fmt, Object... args) {
        __ORG_OSTREAM.printf(pad() + String.format(fmt, args));
    }
    public static void printlnf(String fmt, Object... args) {
        __ORG_OSTREAM.printf(pad() + String.format(fmt, args) + NEWLINE_CHAR);
    }

    /*******************************************************/

    public static void beginBlock(String title) {
        trace.push(title);

        Debug.printf("BEGIN %s\n", title);
        Debug.indent();
    }
    public static void endBlock(){
        Debug.unindent();
        Debug.printf("END %s\n", trace.pop());
    }
}