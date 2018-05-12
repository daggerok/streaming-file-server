package daggerok.service.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.text.DecimalFormat;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class FileItemUtil {

  static final int Kib = 1024;
  public static final long NORMAL_FILE_SIZE = 2 * Kib * Kib;
  static final String ZERO = "0";
  static final String[] UNITS = new String[]{ "b", "kb", "Mb", "Gb", "Tb" };
  static final String DECIMAL_FORMAT_PATTERN = "#,##0.#";
  static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(DECIMAL_FORMAT_PATTERN);

  public static boolean isLarge(final long size) {
    return size > NORMAL_FILE_SIZE;
  }

  public static String toString(final long size) {

    if (size < 1) return ZERO;

    val digitGroups = (int) (Math.log10(size) / Math.log10(Kib));
    val amount = DECIMAL_FORMAT.format(size / Math.pow(Kib, digitGroups));
    val unit = UNITS[digitGroups];

    return format("%s %s", amount, unit);
  }
}
