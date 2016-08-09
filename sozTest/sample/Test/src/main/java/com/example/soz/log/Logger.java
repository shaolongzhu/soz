package com.example.soz.log;

import android.util.Log;

/**
 * log info, using for adding log for critical path
 * Created by zhushaolong on 7/14/16.
 */
public class Logger {
    private String tag = "SOZ";// tag flag
    private Level level = Level.info;

    public Logger(String tag) {
        this.tag = tag;
    }

    /**
     * verbose log
     * @param msg
     */
    public void v(String msg) {
        if (level.ordinal() <= Level.verbose.ordinal()) {
            this.callSysLog(Level.verbose, msg);
        }
    }

    /**
     * debug log
     * @param msg
     */
    public void d(String msg) {
        if (level.ordinal() <= Level.debug.ordinal()) {
            this.callSysLog(Level.debug, msg);
        }
    }

    /**
     * info log
     * @param msg
     */
    public void i(String msg) {
        if (level.ordinal() <= Level.info.ordinal()) {
            this.callSysLog(Level.info, msg);
        }
    }

    /**
     * warn log
     * @param msg
     */
    public void w(String msg) {
        if (level.ordinal() <= Level.warn.ordinal() ) {
            this.callSysLog(Level.warn, msg);
        }
    }

    /**
     * error log
     * @param msg
     */
    public void e(String msg) {
        if (level.ordinal() <= Level.error.ordinal()) {
            this.callSysLog(Level.error, msg);
        }
    }

    /**
     * call system Log for print
     * @param level
     * @param msg
     */
    private void callSysLog(Level level, String msg) {
        switch (level) {
            case verbose:
                Log.v(tag, msg);
                break;
            case debug:
                Log.d(tag, msg);
                break;
            case info:
                Log.i(tag, msg);
                break;
            case warn:
                Log.w(tag, msg);
                break;
            case error:
                Log.e(tag, msg);
                break;
            default:
                break;
        }
    }
}
