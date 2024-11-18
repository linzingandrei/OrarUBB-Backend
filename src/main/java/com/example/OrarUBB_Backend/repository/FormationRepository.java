package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.Formation;
import com.example.OrarUBB_Backend.dto.GroupResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Integer> {

    List<Formation> findByCode(String code);

    List<Formation> findByYear(int year);

    List<Formation> findByAcademicSpecialization_AcademicSpecializationId(int academicSpecializationId);

    List<Formation> findByFormationLevel(int formationLevel);

    List<Formation> findByComponents(String components);

    @Query("SELECT f.code " +
            "FROM Formation f " +
            "WHERE f.academicSpecialization.academicSpecializationId = :academicSpecializationId AND f.year = :year AND f.formationLevel = 2")
    List<String> getAllGroupsForSpecializationInAYear(int academicSpecializationId, int year);

    @Query("SELECT f.code " +
            "FROM Formation f " +
            "WHERE f.formationLevel = 2 AND " +
            "f.academicSpecialization.academicSpecializationId = " +
            "(SELECT academicSpecialization.academicSpecializationId FROM Formation WHERE code = :yearCode) AND " +
            "f.year = (SELECT year FROM Formation WHERE code = :yearCode)")
    List<String> getAllGroupsWithYearCode(String yearCode);

    @Query("SELECT f.code FROM Formation f WHERE f.components  LIKE %:code% AND f.formationLevel = 1")
    public String getYearCodeForGroup(@Param("code") String code);

    @Query("SELECT f.code FROM Formation f WHERE f.components LIKE %:code% AND f.formationLevel = 2")
    public String getGroupForSemiGroup(@Param("code") String code);

    @Query("SELECT f.components FROM Formation  f WHERE f.code LIKE %:code% AND f.formationLevel = 2")
    public String getComponentsForGroup(@Param("code") String code);

    @Query("SELECT f.components FROM Formation f WHERE f.code LIKE :code and f.formationLevel = 1")
    public String getGroupsForYearCode(String code);

    @Query("SELECT f.code FROM Formation f WHERE f.code LIKE :code AND f.formationLevel = 1")
    public List<String> getYearCode(String code);

    @Query("SELECT f.code FROM Formation f WHERE f.code LIKE :code AND f.formationLevel = 2")
    public List<String> getGroupCode(String code);


    @Query("SELECT f.code FROM Formation f WHERE f.code LIKE :code AND f.formationLevel = 3")
    public List<String> getSubgroupCode(String code);
}
