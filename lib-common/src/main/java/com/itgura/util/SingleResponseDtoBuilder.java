package com.itgura.util;

public interface SingleResponseDtoBuilder<SingleResult, SingleResponse> {
	SingleResponse buildCreateResponseDto(SingleResult singleResult);	
}