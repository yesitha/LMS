package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgura.entity.AClass;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementResponseDto {
    @JsonProperty("announcement_id")
    private UUID announcementId;
    @JsonProperty("announcement_title")
    private String announcementTitle;
    @JsonProperty("announcement_description")
    private String announcementDescription;
    @JsonProperty("announcement_on")
    private Date announcementOn;
    @JsonProperty("announcement_by")
    private UUID announcementBy;


}
