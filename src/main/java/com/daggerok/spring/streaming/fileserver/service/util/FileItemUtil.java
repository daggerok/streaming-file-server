package com.daggerok.spring.streaming.fileserver.service.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

@Slf4j
public final class FileItemUtil {

    static final int Kib = 1024;
    static final String ZERO = "0";
    static final String[] UNITS = new String[]{"b", "kb", "Mb", "Gb", "Tb"};
    static final String DECIMAL_FORMAT_PATTERN = "#,##0.#";
    static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(DECIMAL_FORMAT_PATTERN);

    public static final long NORMAL_FILE_SIZE = 2 * Kib * Kib;

    public static boolean isLarge(long size) {
        return size > NORMAL_FILE_SIZE;
    }

    public static long getSize(Path path) {
        long size = 0;

        try {
            size = Files.size(path);
        } catch (Exception e) {
            log.error("cant parse file size for {}: {}", path, e.getMessage());
        }
        return size;
    }

    public static String toString(long size) {
        if (size < 1) return ZERO;

        final int digitGroups = (int) (Math.log10(size) / Math.log10(Kib));
        final String amount = DECIMAL_FORMAT.format(size / Math.pow(Kib, digitGroups));
        final String unit = UNITS[digitGroups];

        return String.format("%s %s", amount, unit);
    }

    private FileItemUtil() {}
}
