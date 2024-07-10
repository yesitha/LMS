package com.itgura.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.annotation.Nullable;
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
public class saveContentPaymentRequest {
    @JsonProperty(value = "order_id")
    @NotNull(message = "order id is required")
    @ApiModelProperty(required = true, value = "order id is required")
    private String orderId;

    @JsonProperty(value = "studentEmail")
    @NotNull(message = "student email is required")
    @ApiModelProperty(required = true, value = "student email is required")
    private String studentEmail;

    @JsonProperty(value = "contentId")
    @NotNull(message = "content id is required")
    @ApiModelProperty(required = true, value = "content id is required")
    private UUID contentId;

    @JsonProperty(value = "paymentAmount")
    @NotNull(message = "payment amount is required")
    @ApiModelProperty(required = true, value = "payment amount is required")
    private double paymentAmount;


    @JsonProperty(value = "note")
    private String note;
}
