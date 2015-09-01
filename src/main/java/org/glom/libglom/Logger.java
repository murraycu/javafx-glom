package org.glom.libglom;

import java.util.logging.Level;

/**
 * Created by murrayc on 9/1/15.
 */
public class Logger {
    public static void log(final String message) {
        final java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();
        logger.log(Level.WARNING, message);
    }

    public static void log(final String message, final Exception e) {
        final java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();
        logger.log(Level.WARNING, message, e);
    }
}
