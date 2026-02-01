package utils;

public final class Debug {

    private static int indent = 0;
    private static final String IND = "    "; // 4 spaces

    private static String pad() {
        return IND.repeat(Math.max(0, indent));
    }

    // change indent (+n / -n)
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

    // println
    public static void println(String s) {
        System.out.println(pad() + s);
    }

    public static void println() {
        System.out.println();
    }

    // print
    public static void print(String s) {
        System.out.print(pad() + s);
    }

    // printf
    public static void printf(String fmt, Object... args) {
        System.out.printf(pad() + fmt, args);
    }

    private Debug() {}
}