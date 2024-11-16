package com.example.OrarUBB_Backend.repository;
import com.example.OrarUBB_Backend.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByName(String name);

    List<Room> findByAddress(String address);
}
