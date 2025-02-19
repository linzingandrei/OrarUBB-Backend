package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.ClassInstance;
import com.example.OrarUBB_Backend.service.UserClassRelationService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.OrarUBB_Backend.domain.UserClassRelation;
import com.example.OrarUBB_Backend.domain.UserClassRelationPK;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserClassRelationRepository extends JpaRepository<UserClassRelation, UserClassRelationPK>{

    List<UserClassRelation> findByUsername(String username);
    @Modifying
    @Transactional
    @Query("DELETE FROM UserClassRelation ucr WHERE ucr.username = :username")
     void removeUserClassRelationByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_class_relation (username, class_id) VALUES (:username, :classId)", nativeQuery = true)
    void addUserClassRelation(String username, UUID classId);



    default void addAllUserClassRelations(List<UserClassRelation> userClassRelations) {
        this.saveAll(userClassRelations);
    }
}
