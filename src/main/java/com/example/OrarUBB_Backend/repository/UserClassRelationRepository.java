package com.example.OrarUBB_Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OrarUBB_Backend.domain.UserClassRelation;
import com.example.OrarUBB_Backend.domain.UserClassRelationPK;

@Repository
public interface UserClassRelationRepository extends JpaRepository<UserClassRelation, UserClassRelationPK>{

}
