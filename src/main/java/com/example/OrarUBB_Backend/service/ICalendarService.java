package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.SemesterDates;
import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import org.springframework.stereotype.Service;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.data.CalendarOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ICalendarService {

    private final ClassInstanceService classInstanceService;

    public ICalendarService(ClassInstanceService classInstanceService) {
        this.classInstanceService = classInstanceService;

        if (this.classInstanceService != null) {
            try {
                // Generate a test calendar and save it to a file
                Calendar testCalendar = generateCalendarForGroup("925", "ro-RO");
                if (testCalendar != null) {
                    saveCalendarToFile(testCalendar, "test_schedule.ics");
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log the exception for debugging
            }
        } else {
            System.out.println("classInstanceService is not initialized. Skipping test calendar generation.");
        }
    }

    private DayOfWeek mapRomanianDayToDayOfWeek(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "luni":
                return DayOfWeek.MONDAY;
            case "marti":
                return DayOfWeek.TUESDAY;
            case "miercuri":
                return DayOfWeek.WEDNESDAY;
            case "joi":
                return DayOfWeek.THURSDAY;
            case "vineri":
                return DayOfWeek.FRIDAY;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeek);
        }
    }

    private LocalDate findNextClassDate(LocalDate start, String dayOfWeek) {
        DayOfWeek targetDay = mapRomanianDayToDayOfWeek(dayOfWeek);
        LocalDate nextDate = start;

        while (nextDate.getDayOfWeek() != targetDay) {
            nextDate = nextDate.plusDays(1);
        }

        nextDate = SemesterDates.adjustForHolidays(nextDate);

        return nextDate;
    }

    public Calendar generateCalendarForGroup(String groupCode, String language) {
        List<ClassInstanceResponse> classes = classInstanceService.getClassesForGroup(groupCode, language);
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//Facultatea de Matematica si Informatica//Orar//RO"));
        calendar.add(ImmutableVersion.VERSION_2_0);
        calendar.add(ImmutableCalScale.GREGORIAN);

        for (ClassInstanceResponse classInstance : classes) {
            VEvent event = createEventFromClassInstance(classInstance);
            if (event != null) {
                calendar.add(event);
            }
        }

        return calendar;
    }

    public void saveCalendarToFile(Calendar calendar, String fileName) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(fileName)) {
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
        }
    }

    private VEvent createEventFromClassInstance(ClassInstanceResponse classInstance) {
        // Define the time zone
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Bucharest"); // UBB timezone
        VTimeZone tz = timezone.getVTimeZone();

        LocalDate currentDate = findNextClassDate(SemesterDates.SEMESTER_START, classInstance.getClassDay());

        // Adjust start date for even weeks
        if (classInstance.getFrequency() == 2) { // Even weeks
            currentDate = currentDate.plusWeeks(1);
        }

        // Create ZonedDateTime objects for start and end
        ZonedDateTime startDateTime = currentDate
                .atTime(classInstance.getStartHour(), 0)
                .atZone(ZoneId.of("Europe/Bucharest"));

        ZonedDateTime endDateTime = currentDate
                .atTime(classInstance.getEndHour(), 0)
                .atZone(ZoneId.of("Europe/Bucharest"));

        // Create the VEvent using ZonedDateTime
        VEvent event = new VEvent(startDateTime, endDateTime, classInstance.getCourseInstanceName());

        // Add recurrence rules based on frequency
        String recurrenceRule = generateRecurrenceRule(classInstance.getFrequency());
        if (recurrenceRule != null) {
            event.add(new RRule(recurrenceRule));
        }

        // Add other event properties
        event.add(new Location(classInstance.getRoom()));
        event.add(new Description(
                String.format("Formation: %s\nType: %s\nTeacher: %s",
                        classInstance.getFormation(),
                        classInstance.getClassType(),
                        classInstance.getTeacher()
                )
        ));
        event.add(new Uid(classInstance.getClassId().toString()));
        return event;
    }

    private String generateRecurrenceRule(int frequency) {
        String frequencyType = "WEEKLY";
        LocalDate endDate = SemesterDates.SEMESTER_END;

        switch (frequency) {
            case 0: // Every week
                return String.format("FREQ=%s;UNTIL=%s", frequencyType, endDate.toString().replace("-", ""));
            case 1: // Odd weeks
                return String.format("FREQ=%s;INTERVAL=2;UNTIL=%s;WKST=MO", frequencyType, endDate.toString().replace("-", ""));
            case 2: // Even weeks
                return String.format("FREQ=%s;INTERVAL=2;UNTIL=%s;WKST=MO", frequencyType, endDate.toString().replace("-", ""));
            default:
                return null;
        }
    }

    private DateTime convertToDateTime(LocalDate date, int hour) {
        return new DateTime(Date.from(date.atTime(hour, 0).atZone(ZoneId.systemDefault()).toInstant()));
    }

}
