package com.example.OrarUBB_Backend.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SemesterDates {

    public static final LocalDate SEMESTER_START = LocalDate.of(2025, 2, 24);
    public static final LocalDate SEMESTER_END = LocalDate.of(2025, 6, 8);

    private static final List<Holiday> HOLIDAYS = Arrays.asList(
            new Holiday(LocalDate.of(2025, 4, 21), LocalDate.of(2025, 4, 27))
    );

    public static boolean isHoliday(LocalDate date) {
        for (Holiday holiday : HOLIDAYS) {
            if (!date.isBefore(holiday.start) && !date.isAfter(holiday.end)) {
                return true;
            }
        }
        return false;
    }

    public static LocalDate adjustForHolidays(LocalDate date) {
        int weekIncrement = 0;
        LocalDate adjustedDate = date;

        while (isHoliday(adjustedDate)) {
            Holiday currentHoliday = getCurrentHoliday(adjustedDate);
            weekIncrement += currentHoliday.getWeekSpan();
            adjustedDate = currentHoliday.end.plusDays(1);
        }

        return adjustedDate.plusWeeks(weekIncrement);
    }

    public static List<LocalDate> getAllHolidayDates() {
        List<LocalDate> holidayDates = new ArrayList<>();
        for (Holiday holiday : HOLIDAYS) {
            LocalDate current = holiday.start;
            while (!current.isAfter(holiday.end)) {
                holidayDates.add(current);
                current = current.plusDays(1);
            }
        }
        return holidayDates;
    }

    private static Holiday getCurrentHoliday(LocalDate date) {
        return HOLIDAYS.stream()
                .filter(holiday -> !date.isBefore(holiday.start) && !date.isAfter(holiday.end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No holiday found for date: " + date));
    }

    public static int getEffectiveWeekIndex(LocalDate date) {
        LocalDate currentDate = SEMESTER_START;
        int weekIndex = 0;

        while (!currentDate.isAfter(date)) {
            if (!isHoliday(currentDate) && currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                weekIndex++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return weekIndex;
    }

    public static boolean isOddWeek(LocalDate date) {
        int effectiveWeekIndex = getEffectiveWeekIndex(date);
        return effectiveWeekIndex % 2 != 0;
    }

    private static class Holiday {
        LocalDate start;
        LocalDate end;

        Holiday(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
        }

        int getWeekSpan() {
            return (int) (start.until(end).getDays() / 7) + 1;
        }
    }
}
