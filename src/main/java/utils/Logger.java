package utils;

import no.hvl.dat110.rpc.RPCCommon;

import java.util.HashMap;

/*
    * Logger utility class for debugging and logging messages.
    * NOTES: Set DEBUG = true for more messagess
 */

public class Logger {
    public static final boolean DEBUG = false;
    public static final HashMap<String, Integer> DEBUG_WHO_MAP = new HashMap<>();

    public static void beginBlock(String blockName) {
        Debug.beginBlock(blockName);
    }
    public static void endBlock() {
        Debug.endBlock();
    }

    public static void log(String message) {
        if (DEBUG) {
            Debug.println(message);
        }
    }

    public static void logf(String format, Object... args) {
        if (DEBUG) {
            Debug.printf(format, args);
        }
    }

    public static void log(int level, String message) {
        if (DEBUG) {
            Debug.indent(1);
            Debug.println(message);
            Debug.indent(-1);
        }
    }

    public static void logf(int level, String format, Object... args) {
        if (DEBUG) {
            Debug.indent(1);
            Debug.printf(format, args);
            Debug.indent(-1);
        }
    }

//

    public static void log(String who, String message) {
        if (DEBUG && DEBUG_WHO_MAP.containsKey(who)) {
            Debug.indent(1);
            Debug.println(message);
            Debug.indent(-1);
        }
    }

    public static void logf(String who, String format, Object... args) {
        if (DEBUG && DEBUG_WHO_MAP.containsKey(who)) {
            Debug.indent(1);
            Debug.printf(format, args);
            Debug.indent(-1);
        }
    }
}
