package com.example.OrarUBB_Backend.repository;
import com.example.OrarUBB_Backend.domain.Room;
import com.example.OrarUBB_Backend.dto.RoomAvailability;

import jakarta.annotation.Nonnull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByName(String name);

    List<Room> findByAddress(String address);

    List<Room> findByRoomId(Integer id);

    @Query(value = """
        SELECT 
            d.day_name_locale AS classDay,
            JSON_AGG(
                JSON_BUILD_OBJECT(
                    'roomName', r.name,
                    'frequency', c.frequency
                )
                ORDER BY r.name
            ) AS rooms,
            c.start_hour AS startHour
        FROM 
            room r
        JOIN 
            class_instance c ON r.room_id = c.room_id
        JOIN 
            day_definition_locale d ON c.class_day_id = d.day_definition_id
        WHERE 
            d.language_tag = :language
        GROUP BY 
            d.day_name_locale, d.day_definition_id, c.start_hour
        ORDER BY 
            d.day_definition_id, c.start_hour
    """, nativeQuery = true)
    List<Object[]> findRoomAvailabilityByLanguage(@Param("language") String language);
}