package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import com.example.OrarUBB_Backend.service.FormationService;
import com.example.OrarUBB_Backend.service.UserClassRelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserDataController {
    private final UserClassRelationService userClassRelationService;
    @GetMapping("/classes/{user_id}/{language}")
    public ResponseEntity<List<ClassInstanceResponse>> getClassesForUser(
            @PathVariable("user_id") String userId,
            @PathVariable("language") String language) {

        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");

        if (validLanguages.contains(language)) {
            List<ClassInstanceResponse> classes = userClassRelationService.getClassesForUser(userId, language);
            return ResponseEntity.ok(classes);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/classes/{user_id}/{group}")
    public ResponseEntity<String> mapGroupCoursesToUser(
            @PathVariable("user_id") String userId,
            @PathVariable("group") String group
    )
    {
        System.out.println("Path /user/classes/" + userId + "/" + group + " called");
        userClassRelationService.mapGroupCoursesToUser(userId, group);
                return ResponseEntity.ok("Group classes mapped to user");
    }
}
