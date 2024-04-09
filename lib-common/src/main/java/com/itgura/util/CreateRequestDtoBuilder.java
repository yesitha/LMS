package com.itgura.util;

import java.util.UUID;

public interface CreateRequestDtoBuilder<CreateRequest, CreateRequestDto> extends RequestDtoBuilder{
	Object buildCreateDto(CreateRequest request, UUID id);
}
