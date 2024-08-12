package com.itgura.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QuestionDTO {
    private String questionText;
    private String questionType; // e.g., MCQ, Essay
    private Integer marks;
    private List<OptionDTO> options; // Only for MCQ
    private List<FileDTO> files;

}
