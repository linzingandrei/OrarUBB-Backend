package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.domain.Room;
import com.example.OrarUBB_Backend.dto.RoomAvailability;
import com.example.OrarUBB_Backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/rooms-schedule/{language}")
    public ResponseEntity<List<RoomAvailability>> getRoomSchedule(@PathVariable("language") String language) {
        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");

        if (validLanguages.contains(language)) {
            List<RoomAvailability> roomsSchedule = roomService.roomAvailability(language);
            return ResponseEntity.ok(roomsSchedule);
        }

        return ResponseEntity.badRequest().build();
    }
    
}
