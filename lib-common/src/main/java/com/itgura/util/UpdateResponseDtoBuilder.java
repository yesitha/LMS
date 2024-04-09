package com.itgura.util;

public interface UpdateResponseDtoBuilder<Entity, UpdateResponseDto> extends ResponseDtoBuilder {
	UpdateResponseDto buildUpdateResponseDto(Entity entity);
}
