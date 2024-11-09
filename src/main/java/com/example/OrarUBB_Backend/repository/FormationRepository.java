package com.example.OrarUBB_Backend.repository;
import com.example.OrarUBB_Backend.domain.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface FormationRepository extends JpaRepository<Formation, UUID> {

    List<Formation> findByCode(String code);

    List<Formation> findByYear(int year);

    List<Formation> findByFormationLevel(int formationLevel);

    List<Formation> findByComponents(String components);
}
