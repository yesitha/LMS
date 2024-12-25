package com.itgura.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionFileResponse {
    private UUID id;
    private UUID imageUrl; // URL to the image file
}

