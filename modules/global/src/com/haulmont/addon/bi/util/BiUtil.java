package com.haulmont.addon.bi.util;

import org.apache.commons.lang.StringUtils;

public class BiUtil {
    private static final String SAIKU_EXTENSION = ".saiku";
    private static final String PENTAHO_EXTENSION = ".xanalyzer";

    private BiUtil() {
    }

    public static boolean isSaiku(String reportPath) {
        return StringUtils.containsIgnoreCase(reportPath, SAIKU_EXTENSION);
    }

    public static boolean isPentaho(String reportPath) {
        return StringUtils.containsIgnoreCase(reportPath, PENTAHO_EXTENSION);
    }
}
