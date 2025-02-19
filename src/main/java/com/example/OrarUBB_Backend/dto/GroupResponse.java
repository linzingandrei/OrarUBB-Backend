package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupResponse {
    private List<String> groups;
}
