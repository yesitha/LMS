package com.itgura.paymentservice.dto.request;


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
public class saveMonthlyPaymentRequest {

    @JsonProperty(value = "order_id")
    @NotNull(message = "order id is required")
    @ApiModelProperty(required = true, value = "order id is required")
    private UUID orderId;

    @JsonProperty(value = "studentEmail")
    @NotNull(message = "student email is required")
    @ApiModelProperty(required = true, value = "student email is required")
    private String studentEmail;


    @JsonProperty(value = "paymentMonths")
    @NotNull(message = "payment months is required")
    @ApiModelProperty(required = true, value = "payment months is required")
    private int[] paymentMonths;

    @JsonProperty(value = "paymentAmount")
    @NotNull(message = "payment amount is required")
    @ApiModelProperty(required = true, value = "payment amount is required")
    private double paymentAmount;

    @JsonProperty(value = "classId")
    @NotNull(message = "class id is required")
    @ApiModelProperty(required = true, value = "class id is required")
    private UUID classId;

    @JsonProperty(value = "note")

    private String note;



}
