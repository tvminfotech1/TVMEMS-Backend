package com.tvm.internal.tvm_internal_project.util;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {

    public static String formatMonthYear(String monthValue) {
        if (monthValue == null || monthValue.isEmpty()) {
            return "";
        }
        try {
            YearMonth ym = YearMonth.parse(monthValue);
            String monthName = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            return monthName + "-" + ym.getYear();
        } catch (Exception e) {
            return monthValue;
        }
    }
}
