package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class addSessionToMonthRequest {

    @JsonProperty(value = "classId")
    @NotNull(message = "class id is required")
    @ApiModelProperty(required = true, value = "class id is required")
    private UUID classId;

    @JsonProperty(value = "month")
    @NotNull(message = "month is required")
    @ApiModelProperty(required = true, value = "month is required")
    private int month;

    @JsonProperty(value = "year")
    @NotNull(message = "year is required")
    @ApiModelProperty(required = true, value = "year is required")
    private int year;

    @JsonProperty(value = "sessionId")
    @NotNull(message = "session id is required")
    @ApiModelProperty(required = true, value = "session id is required")
    private UUID sessionId;

}
