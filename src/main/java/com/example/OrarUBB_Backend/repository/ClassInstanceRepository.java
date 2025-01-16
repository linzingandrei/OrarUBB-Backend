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

    @Query("SELECT ci FROM ClassInstance ci WHERE ci.roomId = :roomId ORDER BY ci.dayId, ci.startHour ASC")
    public List<ClassInstance> findByRoomId(@Param("roomId") int roomId);

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
    @Query("SELECT cls_i.classId, day_def_loc.dayNameLocale, cls_i.startHour, cls_i.endHour, cls_i.frequency, r.name,f.code,cls_type_loc.classTypeLocale,crs_i.courseCode, " +
    "crs_loc.courseNameLocale,ac_r_loc.academicRankAbbreviationLocaleName,t.firstName,t.surname " +
    "FROM ClassInstance cls_i JOIN Formation f ON cls_i.formationId = f.formationId AND f.code = :groupCode JOIN ClassTypeLocale cls_type_loc ON cls_i.classTypeId = cls_type_loc.classTypeId AND cls_type_loc.languageTag = :languageTag " +
    "JOIN Room r ON cls_i.roomId = r.roomId JOIN Teacher t ON cls_i.teacherId = t.teacherId JOIN AcademicRankLocale ac_r_loc ON t.academicRank.academicRankId = ac_r_loc.academicRank.academicRankId AND ac_r_loc.academicRankLocaleKey.languageTag = :languageTag " +
    "JOIN DayDefinitionLocale day_def_loc ON day_def_loc.dayDefinition.dayId = cls_i.dayId AND day_def_loc.languageTag = :languageTag " +
    "JOIN CourseInstance crs_i ON cls_i.courseInstanceId = crs_i.courseInstanceId JOIN CourseCodeNameLocale crs_loc ON crs_loc.courseCodeNameId = crs_i.courseId AND crs_loc.languageTag = :languageTag " +
    "ORDER BY day_def_loc.dayDefinition.dayId, cls_i.startHour ASC")
    public List<Object[]> findClassInstancesByGroupAndLanguageTag(@Param("groupCode") String groupCode, @Param("languageTag") String languageTag);

    @Query("SELECT cls_i.classId, day_def_loc.dayNameLocale, cls_i.startHour, cls_i.endHour, cls_i.frequency, r.name,f.code,cls_type_loc.classTypeLocale,crs_i.courseCode, " +
            "crs_loc.courseNameLocale,ac_r_loc.academicRankAbbreviationLocaleName,t.firstName,t.surname " +
            "FROM ClassInstance cls_i JOIN Formation f ON cls_i.classId = :classId AND cls_i.formationId = f.formationId JOIN ClassTypeLocale cls_type_loc ON cls_i.classTypeId = cls_type_loc.classTypeId AND cls_type_loc.languageTag = :languageTag " +
            "JOIN Room r ON cls_i.roomId = r.roomId JOIN Teacher t ON cls_i.teacherId = t.teacherId JOIN AcademicRankLocale ac_r_loc ON t.academicRank.academicRankId = ac_r_loc.academicRank.academicRankId AND ac_r_loc.academicRankLocaleKey.languageTag = :languageTag " +
            "JOIN DayDefinitionLocale day_def_loc ON day_def_loc.dayDefinition.dayId = cls_i.dayId AND day_def_loc.languageTag = :languageTag " +
            "JOIN CourseInstance crs_i ON cls_i.courseInstanceId = crs_i.courseInstanceId JOIN CourseCodeNameLocale crs_loc ON crs_loc.courseCodeNameId = crs_i.courseId AND crs_loc.languageTag = :languageTag " +
            "ORDER BY day_def_loc.dayDefinition.dayId, cls_i.startHour ASC")
    public List<Object[]> findClassInstanceByClassId(UUID classId, @Param("languageTag") String languageTag);

    @Query("SELECT cls_i.classId, day_def_loc.dayNameLocale, cls_i.startHour, cls_i.endHour, cls_i.frequency, r.name,f.code,cls_type_loc.classTypeLocale,crs_i.courseCode, " +
            "crs_loc.courseNameLocale,ac_r_loc.academicRankAbbreviationLocaleName,t.firstName,t.surname " +
            "FROM ClassInstance cls_i JOIN Formation f ON cls_i.formationId = f.formationId  JOIN ClassTypeLocale cls_type_loc ON cls_i.classTypeId = cls_type_loc.classTypeId AND cls_type_loc.languageTag = :languageTag " +
            "JOIN Room r ON cls_i.roomId = r.roomId and r.name = :roomName JOIN Teacher t ON cls_i.teacherId = t.teacherId JOIN AcademicRankLocale ac_r_loc ON t.academicRank.academicRankId = ac_r_loc.academicRank.academicRankId AND ac_r_loc.academicRankLocaleKey.languageTag = :languageTag " +
            "JOIN DayDefinitionLocale day_def_loc ON day_def_loc.dayDefinition.dayId = cls_i.dayId AND day_def_loc.languageTag = :languageTag " +
            "JOIN CourseInstance crs_i ON cls_i.courseInstanceId = crs_i.courseInstanceId JOIN CourseCodeNameLocale crs_loc ON crs_loc.courseCodeNameId = crs_i.courseId AND crs_loc.languageTag = :languageTag " +
            "ORDER BY day_def_loc.dayDefinition.dayId, cls_i.startHour ASC ")
    public List<Object[]> findClassInstancesByRoomNameAndLanguageTag(@Param("roomName") String roomName, @Param("languageTag") String languageTag);

    @Query("SELECT cls_i.classId, day_def_loc.dayNameLocale, cls_i.startHour, cls_i.endHour, cls_i.frequency, r.name,f.code,cls_type_loc.classTypeLocale,crs_i.courseCode, " +
            "crs_loc.courseNameLocale,ac_r_loc.academicRankAbbreviationLocaleName,t.firstName,t.surname " +
            "FROM ClassInstance cls_i JOIN Formation f ON cls_i.formationId = f.formationId JOIN ClassTypeLocale cls_type_loc ON cls_i.classTypeId = cls_type_loc.classTypeId AND cls_type_loc.languageTag = :languageTag " +
            "JOIN Room r ON cls_i.roomId = r.roomId JOIN Teacher t ON cls_i.teacherId = t.teacherId and t.codeName = :teacher_code_name JOIN AcademicRankLocale ac_r_loc ON t.academicRank.academicRankId = ac_r_loc.academicRank.academicRankId AND ac_r_loc.academicRankLocaleKey.languageTag = :languageTag " +
            "JOIN DayDefinitionLocale day_def_loc ON day_def_loc.dayDefinition.dayId = cls_i.dayId AND day_def_loc.languageTag = :languageTag " +
            "JOIN CourseInstance crs_i ON cls_i.courseInstanceId = crs_i.courseInstanceId JOIN CourseCodeNameLocale crs_loc ON crs_loc.courseCodeNameId = crs_i.courseId AND crs_loc.languageTag = :languageTag " +
            "ORDER BY day_def_loc.dayDefinition.dayId, cls_i.startHour ASC")
    public List<Object[]> findClassInstanceByTeacherAndLanguageTag(@Param("teacher_code_name") String teacher_code_name, @Param("languageTag") String languageTag);

//    public void

}
