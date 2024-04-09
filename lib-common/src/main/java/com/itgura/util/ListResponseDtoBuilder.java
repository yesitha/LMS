package com.itgura.util;

public interface ListResponseDtoBuilder <ListResult, ListResponse> extends ResponseDtoBuilder {
	ListResponse buildListResponseDto(ListResult listResult);
}
