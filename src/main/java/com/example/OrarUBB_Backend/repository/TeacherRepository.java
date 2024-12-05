package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    List<Teacher> findByAcademicRank_AcademicRankId(Integer academicRankId);

    List<Teacher> findByFirstNameIgnoreCase(String firstName);

    List<Teacher> findBySurnameIgnoreCase(String surname);

    List<Teacher> findByFirstNameIgnoreCaseAndSurnameIgnoreCase(String firstName, String surname);

    // The reason for a custom query with List<List<String>>
    // is because 1. I am lazy 2. Before, you had to query the database for every professor to get their rank
    // TODO: create a separate class like 'LocalizedTeacher' with fields: teacherId, localizedName and codeName
    // TODO: after that, this method should return List<LocalizedTeacher>
    @Query("SELECT t.teacherId, arl.academicRankAbbreviationLocaleName, t.firstName, t.surname, t.codeName " +
            "FROM Teacher t " +
            "JOIN AcademicRankLocale arl ON t.academicRank.academicRankId = arl.academicRank.academicRankId " +
            "JOIN ClassInstance ci ON ci.teacherId = t.teacherId " +
            "WHERE arl.academicRankLocaleKey.languageTag = :languageTag " +
            "GROUP BY t.teacherId, arl.academicRankAbbreviationLocaleName, t.firstName, t.surname, t.codeName " +
            "HAVING COUNT(ci.classId) > 0")
    List<List<String>> getAllTeacherRanks_LocalizedNames_CodeNames(String languageTag);


    // Not really useful unless you use this not localized
    @Query("SELECT t FROM Teacher t JOIN AcademicRankLocale arl ON t.academicRank.academicRankId = arl.academicRank.academicRankId " +
            "WHERE arl.academicRankLocaleKey.languageTag = :languageTag")
    List<Teacher> getAllTeachersLocalized(String languageTag);

    @Query("SELECT t.teacherId, arl.academicRankAbbreviationLocaleName, t.firstName, t.surname, t.codeName " +
            "FROM Teacher t JOIN AcademicRankLocale arl ON t.academicRank.academicRankId = arl.academicRank.academicRankId " +
            "WHERE arl.academicRankLocaleKey.languageTag = :languageTag AND t.teacherId = :teacherId")
    List<String> getTeacherRank_LocalizedName_CodeNameById(UUID teacherId, String languageTag);

    @Query("SELECT t FROM Teacher t JOIN AcademicRankLocale arl ON t.academicRank.academicRankId = arl.academicRank.academicRankId " +
            "WHERE arl.academicRankLocaleKey.languageTag = :languageTag AND t.teacherId = :teacherId")
    Teacher getTeacherLocalizedById(UUID teacherId, String languageTag);

    @Query("SELECT t FROM Teacher t JOIN AcademicRankLocale arl ON t.academicRank.academicRankId = arl.academicRank.academicRankId " +
            "WHERE arl.academicRankLocaleKey.languageTag = :languageTag AND t.codeName = :codeName")
    Teacher getTeacherLocalizedByCodeName(String codeName, String languageTag);
}
