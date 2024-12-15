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
import net.fortuna.ical4j.data.CalendarOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ICalendarService {

    private final ClassInstanceService classInstanceService;

    public ICalendarService(ClassInstanceService classInstanceService) {
        this.classInstanceService = classInstanceService;
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

    private LocalDate findNextClassDate(LocalDate start, String dayOfWeek, int frequency) {
        DayOfWeek targetDay = mapRomanianDayToDayOfWeek(dayOfWeek);
        LocalDate nextDate = start;
        boolean isOddWeek = SemesterDates.isOddWeek(start);

        while (true) {
            if (nextDate.getDayOfWeek() == targetDay && !SemesterDates.isHoliday(nextDate)) {
                if (frequency == 0 || (frequency == 1 && isOddWeek) || (frequency == 2 && !isOddWeek)) {
                    break;
                }
            }

            nextDate = nextDate.plusDays(1);

            if (!SemesterDates.isHoliday(nextDate) && nextDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                isOddWeek = SemesterDates.isOddWeek(nextDate);
            }
        }

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
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Bucharest");
        VTimeZone tz = timezone.getVTimeZone();

        LocalDate currentDate = findNextClassDate(SemesterDates.SEMESTER_START, classInstance.getClassDay(), classInstance.getFrequency());

        ZonedDateTime startDateTime = currentDate
                .atTime(classInstance.getStartHour(), 0)
                .atZone(ZoneId.of("Europe/Bucharest"));

        ZonedDateTime endDateTime = currentDate
                .atTime(classInstance.getEndHour(), 0)
                .atZone(ZoneId.of("Europe/Bucharest"));

        VEvent event = new VEvent(startDateTime, endDateTime, classInstance.getCourseInstanceName());

        String recurrenceRule = generateRecurrenceRule(classInstance.getFrequency());
        if (recurrenceRule != null) {
            event.add(new RRule(recurrenceRule));
        }

        addExclusionDates(event);

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
            case 0:
                return String.format("FREQ=%s;UNTIL=%s", frequencyType, endDate.toString().replace("-", ""));
            case 1:
                return String.format("FREQ=%s;INTERVAL=2;UNTIL=%s;WKST=MO", frequencyType, endDate.toString().replace("-", ""));
            case 2:
                return String.format("FREQ=%s;INTERVAL=2;UNTIL=%s;WKST=MO", frequencyType, endDate.toString().replace("-", ""));
            default:
                return null;
        }
    }

    private void addExclusionDates(VEvent event) {
        for (LocalDate holiday : SemesterDates.getAllHolidayDates()) {
            DateTime holidayDate = new DateTime(Date.from(holiday.atStartOfDay(ZoneId.of("Europe/Bucharest")).toInstant()));
            event.add(new ExDate(String.valueOf(holidayDate)));
        }
    }
}
