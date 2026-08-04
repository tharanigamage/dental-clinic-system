package com.dental.clinic.util;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isNullorBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidContactNumber(String contactNumber) {
        if (isNullorBlank(contactNumber)) {
            return false;
        }
        return contactNumber.trim().matches("\\d{10}");
    }

    public static boolean isValidName(String name) {
        if (isNullorBlank(name)) {
            return false;
        }
        return name.trim().matches("[a-zA-Z ]{2,100}");
    }

    public static boolean isFutureOrTodayDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(LocalDate.now());
    }

    public static boolean isPositiveId(int id) {
        return id > 0;
    }

    public static LocalDate parseDateSafely(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }

    public static LocalTime parseTimeSafely(String timeStr) {
        try {
            return LocalTime.parse(timeStr);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }
}
