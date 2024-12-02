package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.ClassInstance;
import com.example.OrarUBB_Backend.domain.DayDefinitionLocalePK;
import com.example.OrarUBB_Backend.domain.Room;
import com.example.OrarUBB_Backend.dto.RoomAvailability;
import com.example.OrarUBB_Backend.repository.ClassInstanceRepository;
import com.example.OrarUBB_Backend.repository.DayDefinitionLocaleRepository;
import com.example.OrarUBB_Backend.repository.RoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ClassInstanceRepository classInstanceRepository;
    private final DayDefinitionLocaleRepository dayDefinitionLocaleRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository,
                    ClassInstanceRepository classInstanceRepository,
                    DayDefinitionLocaleRepository dayDefinitionLocaleRepository) {
        this.roomRepository = roomRepository;
        this.classInstanceRepository = classInstanceRepository;
        this.dayDefinitionLocaleRepository = dayDefinitionLocaleRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(int roomId) {
        return roomRepository.findById(roomId);
    }

    public List<RoomAvailability> roomAvailability(String language) {
        List<Object[]> results = roomRepository.findRoomAvailabilityByLanguage(language);
        List<RoomAvailability> availableRooms = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Object[] row : results) {
            try{
                String classDay = (String) row[0];
                String roomsJson = (String) row[1];
                JsonNode rooms = objectMapper.readTree(roomsJson);              
                int startHour = (int) row[2];
                
                RoomAvailability roomAvailability = new RoomAvailability(
                    classDay, 
                    rooms,
                    startHour 
                );
                availableRooms.add(roomAvailability);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return availableRooms;
    }
}
