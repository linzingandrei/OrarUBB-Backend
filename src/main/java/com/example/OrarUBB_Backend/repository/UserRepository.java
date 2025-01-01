package com.example.OrarUBB_Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.OrarUBB_Backend.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
