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


    public String findRoomNameByRoomId(UUID roomId);

    public String findFormationCodeByRoomId(UUID formationId);

    @Query("SELECT c.classTypeLocale FROM ClassTypeLocale c WHERE c.classTypeId = :classTypeId AND c.languageTag = :languageTag")
    public String findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(@Param("classTypeId") UUID classTypeId, @Param("languageTag") String language);

    public String findCourseInstanceCodeInCourseInstanceByCourseInstanceId(UUID courseInstanceId);
}
