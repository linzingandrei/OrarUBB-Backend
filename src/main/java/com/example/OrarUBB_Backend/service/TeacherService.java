package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.AcademicRankLocale;
import com.example.OrarUBB_Backend.domain.Teacher;
import com.example.OrarUBB_Backend.dto.TeacherResponse;
import com.example.OrarUBB_Backend.repository.AcademicRankLocaleRepository;
import com.example.OrarUBB_Backend.repository.AcademicRankRepository;
import com.example.OrarUBB_Backend.repository.TeacherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final AcademicRankLocaleRepository academicRankLocaleRepository;
    private final AcademicRankRepository academicRankRepository;

    public TeacherService(TeacherRepository teacherRepository, AcademicRankLocaleRepository academicRankLocaleRepository, AcademicRankRepository academicRankRepository) {
        this.teacherRepository = teacherRepository;
        this.academicRankLocaleRepository = academicRankLocaleRepository;
        this.academicRankRepository = academicRankRepository;
    }

    @PostConstruct
    public void init() {
        UUID id1 = UUID.fromString("9b04e73a-5c4f-4cbc-831b-d6db270453df");
        Teacher teacher1 = new Teacher(id1, "Sergiu-Adrian", "Darabant", academicRankRepository.getReferenceById(1));
        teacherRepository.save(teacher1);

        UUID id2 = UUID.fromString("54b8e418-62a8-4bfc-9caf-c1b8798e2597");
        Teacher teacher2 = new Teacher(id2, "Dumitru", "Dumitru", academicRankRepository.getReferenceById(2));
        teacherRepository.save(teacher2);
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
        List<Teacher> teachers = teacherRepository.findAll();

        return teachers.stream().map(teacher -> {
            Integer rankId = teacher.getAcademicRank().getAcademicRankId();
            AcademicRankLocale rankLocale = academicRankLocaleRepository
                    .findByAcademicRank_AcademicRankIdAndAcademicRankLocaleKey_LanguageTag(
                            rankId, languageTag);


            String rank = (rankLocale != null) ? rankLocale.getAcademicRankAbbreviationLocaleName() : academicRankRepository.findByAcademicRankId(rankId).getRankName();
            String professorNameWithRank = rank + " " + teacher.getFirstName() + " " + teacher.getSurname();
            return new TeacherResponse(teacher.getTeacherId(), professorNameWithRank);
        }).collect(Collectors.toList());
    }
}
