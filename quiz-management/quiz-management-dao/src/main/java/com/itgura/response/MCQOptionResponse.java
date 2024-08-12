package com.itgura.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQOptionResponse {
    private UUID id;
    private String optionText;
    private Boolean isCorrect;
}

