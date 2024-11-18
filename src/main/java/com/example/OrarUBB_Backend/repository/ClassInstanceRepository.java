package com.example.OrarUBB_Backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.OrarUBB_Backend.domain.ClassInstance;

@Repository
public interface ClassInstanceRepository extends JpaRepository<ClassInstance, UUID> {
    public List<ClassInstance> findByTeacherId(UUID teacherId);

    // public String findClassDayByDayIdAndLanguage(int dayId, String language);
    @Query("SELECT d.dayNameLocale FROM DayDefinitionLocale d WHERE d.dayId = :dayId AND d.languageTag = :languageTag")
    public String findClassDayByDayIdAndLanguage(@Param("dayId") int dayId, @Param("languageTag") String language);

    @Query("SELECT t.firstName FROM Teacher t WHERE t.teacherId = :teacherId")
    public String findTeacherNameByTeacherId(UUID teacherId);

    @Query("SELECT r.name FROM Room r WHERE r.roomId = :roomId")
    public String findRoomNameByRoomId(@Param("roomId") int roomId);

    @Query("SELECT f.code FROM Formation f WHERE f.formationId = :formationId")
    public String findFormationCodeByFormationId(@Param("formationId") UUID formationId);

    @Query("SELECT ci " +
           "FROM ClassInstance ci join ci.courseInstance c " +
           "WHERE c.courseCode = :courseCode ")
    public List<ClassInstance> findByCourseCode(@Param("courseCode") String courseCode);

    @Query("SELECT ci " +
            "FROM ClassInstance ci " +
            "WHERE ci.formation.code = :groupCode")
    public List<ClassInstance> findByGroupCode(@Param("groupCode") String groupCode);

    @Query("SELECT c.classTypeLocale FROM ClassTypeLocale c WHERE c.classTypeId = :classTypeId AND c.languageTag = :languageTag")
    public String findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(@Param("classTypeId") int classTypeId, @Param("languageTag") String language);

    @Query("SELECT c.courseCode FROM CourseInstance c WHERE c.courseInstanceId = :courseInstanceId")
    public String findCourseInstanceCodeInCourseInstanceByCourseInstanceId(@Param("courseInstanceId") UUID courseInstanceId);

    @Query("SELECT ccnl.courseCodeName.courseName " +
            "FROM CourseInstance ci INNER JOIN CourseCodeName ccn on ci.courseId = ccn.courseCodeNameId inner join CourseCodeNameLocale ccnl on ccn.courseCodeNameId = ccnl.courseCodeNameId " +
            "WHERE ci.courseInstanceId = :courseCodeNameId AND ccnl.languageTag = :languageTag")
    public String findCourseNameByCourseInstanceIdAndLanguage(@Param("courseCodeNameId") UUID courseCodeNameId, @Param("languageTag") String language);
}
