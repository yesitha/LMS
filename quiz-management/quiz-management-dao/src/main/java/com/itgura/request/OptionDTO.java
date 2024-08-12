package com.itgura.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionDTO {

    private String optionText;
    private Boolean isCorrect;
    private FileDTO file;

    // Getters and Setters
}
