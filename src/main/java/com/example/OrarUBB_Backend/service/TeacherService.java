package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.Teacher;
import com.example.OrarUBB_Backend.dto.TeacherResponse;
import com.example.OrarUBB_Backend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final AcademicRankLocaleService academicRankLocaleService;

    // @Autowired
    public TeacherService(TeacherRepository teacherRepository, AcademicRankLocaleService academicRankLocaleService) {
        this.teacherRepository = teacherRepository;
        this.academicRankLocaleService = academicRankLocaleService;
    }

    public List<Teacher> getTeachersByAcademicRankId(Integer id) {
        return teacherRepository.findByAcademicRank_AcademicRankId(id);
    }

    public List<Teacher> getTeachersByFirstNameAndSurname(String firstName, String surname) {
        return teacherRepository.findByFirstNameIgnoreCaseAndSurnameIgnoreCase(firstName, surname);
    }

    public Optional<Teacher> getTeacherById(UUID id) {
        return teacherRepository.findById(id);
    }

    public List<TeacherResponse> getTeachersWithLocalizedNames(String languageTag) {
        // TODO: redo the repository.getAllTeacherRanks_LocalizedNames_CodeNames() to return a TeacherLocalized object, more info at method definition
        List<TeacherResponse> teachers = new ArrayList<>();
	System.out.println("teachers:" + teachers);
        List<List<String>> teacherNameParts = teacherRepository.getAllTeacherRanks_LocalizedNames_CodeNames(languageTag);
	System.out.println("teacherNameParts: " + teacherNameParts);
        for (List<String> teacherNamePart : teacherNameParts) {
            String teacherId = teacherNamePart.get(0);
            String teacherAcademicRank = teacherNamePart.get(1);
            String teacherName = teacherNamePart.get(2);
            String teacherSurname = teacherNamePart.get(3);
            String teacherCodeName = teacherNamePart.get(4);
            teachers.add(new TeacherResponse(UUID.fromString(teacherId), teacherAcademicRank + ' ' + teacherName + ' ' + teacherSurname, teacherCodeName));
        }
        return teachers;
    }

    public TeacherResponse getTeacherWithLocalizedNameByCodeName(String codeName, String languageTag) {
        try {
            Teacher teacher = teacherRepository.getTeacherLocalizedByCodeName(codeName, languageTag);
            if (teacher == null) {
                throw new Exception();
            }

            String rankAbbreviationLocaleName = this.academicRankLocaleService.getAcademicRankLocalesByAcademicRankId(teacher.getAcademicRank().getAcademicRankId(), languageTag).getAcademicRankAbbreviationLocaleName();
            String teacherNameWithRank = rankAbbreviationLocaleName + " " + teacher.getFirstName() + " " + teacher.getSurname();
            return new TeacherResponse(teacher.getTeacherId(), teacherNameWithRank, teacher.getCodeName());
        } catch (Exception e) {
            System.out.println("Teacher with code name " + codeName + " not found");
            return null;
        }
    }

    public TeacherResponse getTeacherWithLocalizedNameById(UUID teacherId, String languageTag) {
        try {
            Teacher teacher = teacherRepository.getTeacherLocalizedById(teacherId, languageTag);
            if (teacher == null) {
                throw new Exception();
            }
            String rankAbbreviationLocaleName = this.academicRankLocaleService.getAcademicRankLocalesByAcademicRankId(teacher.getAcademicRank().getAcademicRankId(), languageTag).getAcademicRankAbbreviationLocaleName();
            String teacherNameWithRank = rankAbbreviationLocaleName + " " + teacher.getFirstName() + " " + teacher.getSurname();
            return new TeacherResponse(teacherId, teacherNameWithRank, teacher.getCodeName());

        } catch (Exception e) {
            System.out.println("Teacher with id " + teacherId + " not found");
            return null;
        }
    }
}
