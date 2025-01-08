package com.itgura.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailRequest {
    @NotNull(message = "Registration number is required")
    @JsonProperty("registration_number")
    private Integer registrationNumber;
    @NotBlank(message = "First name is required")
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @JsonProperty("last_name")
    private String lastName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @JsonProperty("email")
    private String email;
    @NotBlank(message = "Mobile number is required")
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @NotNull(message = "Examination year is required")
    @JsonProperty("examination_year")
    private Integer examinationYear;
    @NotBlank(message = "Gender is required")
    @JsonProperty("gender")
    private String gender;
    @NotBlank(message = "School is required")
    @JsonProperty("school")
    private String school;
    @NotNull(message = "Stream ID is required")
    @JsonProperty("stream_id")
    private UUID stream;
    @Valid
    @NotNull(message = "Address is required")
    @JsonProperty("address")
    private AddressRequest address;
}