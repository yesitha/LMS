package com.itgura.response.dto.mapper;


import com.itgura.entity.Material;
import com.itgura.entity.Session;
import com.itgura.entity.Student;

import com.itgura.response.dto.MaterialResponseDto;
import com.itgura.response.dto.SessionResponseDto;
import com.itgura.response.dto.UserDetailsResponse;
import org.apache.commons.lang.ArrayUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Base64.class, java.util.Arrays.class})
public interface UserDetailMapper {
    UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);
    @Mappings({
            @Mapping(target = "studentId", source = "student.studentId"),
            @Mapping(target = "userId", source = "student.userId"),
            @Mapping(target = "registration_number", source = "student.registration_number"),
            @Mapping(target = "firstName", source = "student.firstName"),
            @Mapping(target = "lastName", source = "student.lastName"),
            @Mapping(target = "email", source = "student.email"),
            @Mapping(target = "mobileNumber", source = "student.mobileNumber"),
            @Mapping(target = "examinYear", source = "student.examinYear"),
            @Mapping(target = "gender", source = "student.gender"),
            @Mapping(target = "school", source = "student.school"),
            @Mapping(target = "stream", source = "student.stream.stream"), // Assuming `Stream` has a `name` field
            @Mapping(target = "streamId", source = "student.stream.streamId"), // Assuming `Stream` has a `streamId` field
            @Mapping(target = "addressId", source = "student.address.addressId"),
            @Mapping(target = "houseNameOrNumber", source = "student.address.houseNameOrNumber"),
            @Mapping(target = "line1", source = "student.address.line1"),
            @Mapping(target = "line2", source = "student.address.line2"),
            @Mapping(target = "city", source = "student.address.city"),
            @Mapping(target = "profilePicture", source = "student.profilePicture", qualifiedByName = "mapProfilePicture"),
            @Mapping(target = "profilePictureName", source = "student.profilePictureName")
    })
    UserDetailsResponse toDto(Student student);

    @Named("mapProfilePicture")
    default String mapProfilePicture(Byte[] profilePicture) {
        if (profilePicture != null) {
            byte[] primitiveBytes = ArrayUtils.toPrimitive(profilePicture);
            return Base64.getEncoder().encodeToString(primitiveBytes);
        }
        return null;
    }


}
