package com.dental.clinic.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

        @Test
        void isNullorBlank_returnsTrue_forNull() {
            assertTrue(ValidationUtil.isNullorBlank(null));
        }

        @Test
        void isNullorBlank_returnsTrue_forEmptyString() {
            assertTrue(ValidationUtil.isNullorBlank(""));
        }

        @Test
        void isNullorBlank_returnsTrue_forWhitespaceOnly() {
            assertTrue(ValidationUtil.isNullorBlank("   "));
        }

        @Test
        void isNullorBlank_returnsFalse_forNonBlankString() {
            assertFalse(ValidationUtil.isNullorBlank("Kasun"));
        }

        @Test
        void isValidContactNumber_acceptsExactly10Digits() {
            assertTrue(ValidationUtil.isValidContactNumber("0771234567"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"12345", "12345678901", "07712345ab", ""})
        void isValidContactNumber_rejectsInvalidFormats(String input) {
            assertFalse(ValidationUtil.isValidContactNumber(input));
        }

        @Test
        void isValidContactNumber_rejectsNull() {
            assertFalse(ValidationUtil.isValidContactNumber(null));
        }

        @Test
        void isValidName_acceptsLettersAndSpaces() {
            assertTrue(ValidationUtil.isValidName("Kasun Perera"));
        }

        @Test
        void isValidName_rejectsNumbers() {
            assertFalse(ValidationUtil.isValidName("Kasun123"));
        }

        @Test
        void isValidName_rejectsSingleCharacter() {
            assertFalse(ValidationUtil.isValidName("K"));
        }

        @Test
        void isValidName_rejectsBlank() {
            assertFalse(ValidationUtil.isValidName("   "));
        }

        @Test
        void isFutureOrTodayDate_acceptsToday() {
            assertTrue(ValidationUtil.isFutureOrTodayDate(LocalDate.now()));
        }

        @Test
        void isFutureOrTodayDate_acceptsFutureDate() {
            assertTrue(ValidationUtil.isFutureOrTodayDate(LocalDate.now().plusDays(5)));
        }

        @Test
        void isFutureOrTodayDate_rejectsPastDate() {
            assertFalse(ValidationUtil.isFutureOrTodayDate(LocalDate.now().minusDays(1)));
        }

        @Test
        void isFutureOrTodayDate_rejectsNull() {
            assertFalse(ValidationUtil.isFutureOrTodayDate(null));
        }

        @Test
        void isPositiveId_acceptsPositiveNumber() {
            assertTrue(ValidationUtil.isPositiveId(1));
        }

        @Test
        void isPositiveId_rejectsZero() {
            assertFalse(ValidationUtil.isPositiveId(0));
        }

        @Test
        void isPositiveId_rejectsNegative() {
            assertFalse(ValidationUtil.isPositiveId(-5));
        }

        @Test
        void parseDateSafely_parsesValidDate() {
            assertEquals(LocalDate.of(2026, 8, 30), ValidationUtil.parseDateSafely("2026-08-30"));
        }

        @Test
        void parseDateSafely_returnsNull_forInvalidFormat() {
            assertNull(ValidationUtil.parseDateSafely("not-a-date"));
        }

        @Test
        void parseDateSafely_returnsNull_forNull() {
            assertNull(ValidationUtil.parseDateSafely(null));
        }
    }

