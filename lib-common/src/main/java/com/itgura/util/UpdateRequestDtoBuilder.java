package com.itgura.util;

import java.util.UUID;

public interface UpdateRequestDtoBuilder<UpdateRequest, UpdateRequestDto> {
	Object buildUpdateDto(UpdateRequest request, UUID id);
}
