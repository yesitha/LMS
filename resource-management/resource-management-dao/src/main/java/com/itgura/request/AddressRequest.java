package com.itgura.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    @NotBlank(message = "House name or number is required")
    @JsonProperty("house_name_or_number")
    private String houseNameOrNumber;
    @NotBlank(message = "Address line 1 is required")
    @JsonProperty("line1")
    private String line1;
    @JsonProperty("line2")
    private String line2;
    @NotBlank(message = "City is required")
    @JsonProperty("city")
    private String city;
}