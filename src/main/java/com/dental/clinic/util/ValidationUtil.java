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

    // Validate 10 digit contact number
    public static boolean isValidContactNumber(String contactNumber) {
        if (isNullorBlank(contactNumber)) {
            return false;
        }
        return contactNumber.trim().matches("\\d{10}");
    }

    // Validate name letters and spaces
    public static boolean isValidName(String name) {
        if (isNullorBlank(name)) {
            return false;
        }
        return name.trim().matches("[a-zA-Z ]{2,100}");
    }

    // The date is today or a future date
    public static boolean isFutureOrTodayDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(LocalDate.now());
    }

    // Id is greater than zero
    public static boolean isPositiveId(int id) {
        return id > 0;
    }

    // Convert date
    public static LocalDate parseDateSafely(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }

    // Convert time
    public static LocalTime parseTimeSafely(String timeStr) {
        try {
            return LocalTime.parse(timeStr);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }

    // Validate old and new Sri lankan nic formats
    public static boolean isValidNic(String nic) {
        if (isNullorBlank(nic)) {
            return false;
        }
        String trimmed = nic.trim().toUpperCase();
        return trimmed.matches("\\d{9}[VX]") || trimmed.matches("\\d{12}");
    }
}
