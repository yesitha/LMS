package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse {
    @JsonProperty("student_id")
    private UUID studentId;
    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("registration_number")
    private Integer registration_number;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("examination_year")
    private Integer examinYear;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("school")
    private String school;
    @JsonProperty("stream_id")
    private UUID stream;
    @JsonProperty("stream")
    private String address;
    @JsonProperty("address_id")
    private UUID addressId;
    @JsonProperty("house_name_or_number")
    private String houseNameOrNumber;
    @JsonProperty("line1")
    private String line1;
    @JsonProperty("line2")
    private String line2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("user_roles")
    private String userRoles;
}