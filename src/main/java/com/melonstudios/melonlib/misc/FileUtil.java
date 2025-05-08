package com.melonstudios.melonlib.misc;

import java.io.File;

/**
 * Basic file utilities.
 * @since 1.6
 */
public class FileUtil {
    public static final File USER_HOME = new File(System.getProperty("user.home"));
    public static final File DESKTOP = new File(USER_HOME, "Desktop");
    public static final File DOCUMENTS = new File(USER_HOME, "Documents");
    public static final File DOWNLOADS = new File(USER_HOME, "Downloads");

    public static File desktopFile(String path) {
        return new File(DESKTOP, path);
    }
    public static File documentsFile(String path) {
        return new File(DOCUMENTS, path);
    }
    public static File downloadsFile(String path) {
        return new File(DOWNLOADS, path);
    }
}
